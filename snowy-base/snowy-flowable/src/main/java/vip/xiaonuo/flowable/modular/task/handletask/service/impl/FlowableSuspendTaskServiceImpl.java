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

import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.flowable.modular.instance.param.FlowableInstanceParam;
import vip.xiaonuo.flowable.modular.instance.service.FlowableInstanceService;
import vip.xiaonuo.flowable.modular.task.handletask.operator.FlowableCommonOperator;
import vip.xiaonuo.flowable.modular.task.handletask.service.FlowableSuspendTaskService;

import javax.annotation.Resource;

/**
 * 任务挂起service接口实现类
 *
 * @author xuyuxiang
 * @date 2020/8/4 16:37
 **/
@Service
public class FlowableSuspendTaskServiceImpl implements FlowableSuspendTaskService {

    @Resource
    private FlowableInstanceService flowableInstanceService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void suspend(String taskId) {
        //校验任务是否存在
        Task task = FlowableCommonOperator.me().queryTask(taskId);
        //获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        //挂起流程
        FlowableInstanceParam flowableInstanceParam = new FlowableInstanceParam();
        flowableInstanceParam.setId(processInstanceId);
        flowableInstanceService.activeOrSuspend(flowableInstanceParam, true);
    }
}
