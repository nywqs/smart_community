/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Snowy源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/snowy
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/snowy
6.若您的项目无法满足以上几点，可申请商业授权，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.flowable.modular.task.handletask.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import org.flowable.engine.FormService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.core.context.login.LoginContextHolder;
import vip.xiaonuo.flowable.core.enums.DelegateStatusEnum;
import vip.xiaonuo.flowable.core.utils.BpmCommentUtil;
import vip.xiaonuo.flowable.modular.option.service.FlowableOptionService;
import vip.xiaonuo.flowable.modular.task.handletask.operator.FlowableCommonOperator;
import vip.xiaonuo.flowable.modular.task.handletask.service.FlowableSubmitTaskService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 任务提交service接口实现类
 *
 * @author xuyuxiang
 * @date 2020/8/4 16:37
 **/
@Service
public class FlowableSubmitTaskServiceImpl implements FlowableSubmitTaskService {

    /**
     * 使用此方式解决formService重名问题
     **/
    private final FormService formService;

    @Resource
    private FlowableOptionService flowableOptionService;

    @Resource
    private TaskService taskService;

    public FlowableSubmitTaskServiceImpl(FormService formService) {
        this.formService = formService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submit(String taskId, String comment, Map<String, String> variables, String nextAssignee,
                       String nextDueDate,
                       Integer nextPriority) {
        //校验任务是否存在
        Task task = FlowableCommonOperator.me().queryTask(taskId);
        //获取流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        //获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        //获取当前操作人姓名
        String name = LoginContextHolder.me().getSysLoginUser().getName();
        //生成提交意见
        comment = BpmCommentUtil.genSubmitComment(name, comment);
        //给任务添加意见
        FlowableCommonOperator.me().addComment(taskId, comment);
        if (ObjectUtil.isEmpty(variables)) {
            variables = CollectionUtil.newHashMap();
        }
        //如果是委托任务，需解决任务
        if (ObjectUtil.isNotNull(task.getDelegationState())) {
            if (DelegateStatusEnum.PENDING.toString().equals(task.getDelegationState().name())) {
                //解决任务并设置参数
                taskService.resolveTask(taskId, BeanUtil.beanToMap(variables));
            }
        } else {
            //获取当前操作人id
            Long userId = LoginContextHolder.me().getSysLoginUser().getId();
            String assignee = task.getAssignee();
            if(ObjectUtil.isEmpty(assignee)) {
                //设置办理人为当前用户
                taskService.setAssignee(taskId, Convert.toStr(userId));
            }
            //提交任务并设置参数
            formService.submitTaskFormData(taskId, variables);
        }
        //如果该流程设置了跳过相同处理人则自动完成
        boolean smartComplete = flowableOptionService.getFlowableOptionSmartComplete(processDefinitionId);
        if (smartComplete) {
            FlowableCommonOperator.me().smartCompleteNext(processInstanceId);
        }

        //如果设定了下一任务审批人，则设置该审批人
        if (ObjectUtil.isNotEmpty(nextAssignee)) {
            FlowableCommonOperator.me().setNextAssignee(processInstanceId, nextAssignee);
        }

        //如果设定了下一任务审批期限，则设置审批期限
        if (ObjectUtil.isNotEmpty(nextDueDate)) {
            FlowableCommonOperator.me().setNextDueDate(processInstanceId, nextDueDate);
        }

        //如果设定了下一任务审批优先级，则设置审批优先级
        if (ObjectUtil.isNotEmpty(nextPriority)) {
            FlowableCommonOperator.me().setNextPriority(processInstanceId, nextPriority);
        }
    }

    @Override
    public void save(String taskId, Map<String, String> variables) {
        if (ObjectUtil.isEmpty(variables)) {
            variables = CollectionUtil.newHashMap();
        }
        formService.saveFormData(taskId, variables);
    }
}
