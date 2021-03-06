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
package vip.xiaonuo.flowable.modular.task.donetask.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.flowable.task.api.history.HistoricTaskInstance;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.core.util.PastTimeFormatUtil;
import vip.xiaonuo.flowable.modular.instance.result.FlowableInstanceResult;
import vip.xiaonuo.flowable.modular.instance.service.FlowableInstanceService;
import vip.xiaonuo.flowable.modular.task.donetask.result.FlowableDoneTaskResult;
import vip.xiaonuo.flowable.modular.task.handletask.factory.FlowableAssigneeFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * 已办任务工厂类，用于把flowable返回的实体转换为自定义实体
 *
 * @author xuyuxiang
 * @date 2020/4/20 17:26
 */
public class FlowableDoneTaskFactory {

    @Resource
    private static final FlowableInstanceService flowableInstanceService = SpringUtil.getBean(FlowableInstanceService.class);

    /**
     * 根据已办任务集合和默认分页，返回自定义PageResult
     *
     * @author xuyuxiang
     * @date 2020/4/21 15:07
     */
    public static PageResult<FlowableDoneTaskResult> pageResult(List<HistoricTaskInstance> taskList,
                                                                Page<FlowableDoneTaskResult> defaultPage) {
        List<FlowableDoneTaskResult> flowableDoneTaskResultList =
                convertToFlowableDoneTaskResultList(taskList);
        return new PageResult<>(defaultPage, flowableDoneTaskResultList);
    }

    /**
     * 将已办任务结果转换为自定义结果
     *
     * @author xuyuxiang
     * @date 2020/4/21 15:08
     */
    private static List<FlowableDoneTaskResult> convertToFlowableDoneTaskResultList(List<HistoricTaskInstance> taskList) {
        List<FlowableDoneTaskResult> flowableDoneTaskResultList = CollectionUtil.newArrayList();
        taskList.forEach(historicTaskInstance -> {
            FlowableDoneTaskResult flowableDoneTaskResult = convertToFlowableDoneTaskResult(historicTaskInstance);
            flowableDoneTaskResultList.add(flowableDoneTaskResult);
        });
        return flowableDoneTaskResultList;
    }

    /**
     * 将单个已办任务结果转换为自定义结果
     *
     * @author xuyuxiang
     * @date 2020/4/21 15:09
     */
    private static FlowableDoneTaskResult convertToFlowableDoneTaskResult(HistoricTaskInstance historicTaskInstance) {
        FlowableDoneTaskResult flowableDoneTaskResult = new FlowableDoneTaskResult();
        flowableDoneTaskResult.setId(historicTaskInstance.getId());
        flowableDoneTaskResult.setName(historicTaskInstance.getName());
        flowableDoneTaskResult.setActivityId(historicTaskInstance.getTaskDefinitionKey());
        flowableDoneTaskResult.setExecutionId(historicTaskInstance.getExecutionId());
        flowableDoneTaskResult.setAssignee(historicTaskInstance.getAssignee());
        flowableDoneTaskResult.setAssigneeName(FlowableAssigneeFactory.getAssigneeNameByUserId(historicTaskInstance.getAssignee()));
        flowableDoneTaskResult.setAssigneeInfo(FlowableAssigneeFactory.getAssigneeInfoByUserId(historicTaskInstance.getAssignee()));
        flowableDoneTaskResult.setPriority(historicTaskInstance.getPriority());
        flowableDoneTaskResult.setCreateTime(historicTaskInstance.getCreateTime());
        flowableDoneTaskResult.setFormatCreateTime(PastTimeFormatUtil.formatPastTime(historicTaskInstance.getCreateTime()));
        flowableDoneTaskResult.setClaimTime(historicTaskInstance.getClaimTime());
        flowableDoneTaskResult.setEndTime(historicTaskInstance.getEndTime());
        flowableDoneTaskResult.setFormatEndTime(PastTimeFormatUtil.formatPastTime(historicTaskInstance.getEndTime()));
        flowableDoneTaskResult.setDuration(DateUtil.formatBetween(historicTaskInstance.getDurationInMillis(), BetweenFormater.Level.SECOND));
        flowableDoneTaskResult.setDueDate(historicTaskInstance.getDueDate());

        FlowableInstanceResult procIns = flowableInstanceService.detail(historicTaskInstance.getProcessInstanceId());
        flowableDoneTaskResult.setProcIns(procIns);

        return flowableDoneTaskResult;
    }
}
