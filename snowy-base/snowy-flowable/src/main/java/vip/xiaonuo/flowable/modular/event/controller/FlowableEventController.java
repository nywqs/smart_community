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
package vip.xiaonuo.flowable.modular.event.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.flowable.modular.event.param.FlowableEventParam;
import vip.xiaonuo.flowable.modular.event.service.FlowableEventService;

import javax.annotation.Resource;

/**
 * 流程事件控制器
 *
 * @author xuyuxiang
 * @date 2020/4/17 14:53
 */
@RestController
public class FlowableEventController {

    @Resource
    private FlowableEventService flowableEventService;

    /**
     * 添加流程事件
     *
     * @author xuyuxiang
     * @date 2020/4/17 15:31
     */
    @Permission
    @PostMapping("/flowableEvent/add")
    @BusinessLog(title = "流程事件_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(FlowableEventParam.add.class) FlowableEventParam flowableEventParam) {
        flowableEventService.add(flowableEventParam);
        return new SuccessResponseData();
    }

    /**
     * 删除流程事件
     *
     * @author xuyuxiang
     * @date 2020/4/17 15:31
     */
    @Permission
    @PostMapping("/flowableEvent/delete")
    @BusinessLog(title = "流程事件_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(FlowableEventParam.delete.class) FlowableEventParam flowableEventParam) {
        flowableEventService.delete(flowableEventParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑流程事件
     *
     * @author xuyuxiang
     * @date 2020/4/17 15:32
     */
    @Permission
    @PostMapping("/flowableEvent/edit")
    @BusinessLog(title = "流程事件_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(FlowableEventParam.edit.class) FlowableEventParam flowableEventParam) {
        flowableEventService.edit(flowableEventParam);
        return new SuccessResponseData();
    }

    /**
     * 查看流程事件
     *
     * @author xuyuxiang
     * @date 2020/4/17 15:32
     */
    @Permission
    @GetMapping("/flowableEvent/detail")
    @BusinessLog(title = "流程事件_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(FlowableEventParam.detail.class) FlowableEventParam flowableEventParam) {
        return new SuccessResponseData(flowableEventService.detail(flowableEventParam));
    }

    /**
     * 根据流程定义查询事件列表
     *
     * @author xuyuxiang
     * @date 2020/4/17 15:32
     */
    @Permission
    @GetMapping("/flowableEvent/list")
    @BusinessLog(title = "流程事件_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(@Validated(FlowableEventParam.list.class) FlowableEventParam flowableEventParam) {
        return new SuccessResponseData(flowableEventService.list(flowableEventParam));
    }
}
