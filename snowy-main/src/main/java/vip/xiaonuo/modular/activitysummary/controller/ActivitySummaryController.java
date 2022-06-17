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
package vip.xiaonuo.modular.activitysummary.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.activitysummary.param.ActivitySummaryParam;
import vip.xiaonuo.modular.activitysummary.service.ActivitySummaryService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 活动总结控制器
 *
 * @author jetox
 * @date 2022-06-17 22:55:11
 */
@RestController
public class ActivitySummaryController {

    @Resource
    private ActivitySummaryService activitySummaryService;

    /**
     * 查询活动总结
     *
     * @author jetox
     * @date 2022-06-17 22:55:11
     */
    @Permission
    @GetMapping("/activitySummary/page")
    @BusinessLog(title = "活动总结_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(ActivitySummaryParam activitySummaryParam) {
        return new SuccessResponseData(activitySummaryService.page(activitySummaryParam));
    }

    /**
     * 添加活动总结
     *
     * @author jetox
     * @date 2022-06-17 22:55:11
     */
    @Permission
    @PostMapping("/activitySummary/add")
    @BusinessLog(title = "活动总结_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(ActivitySummaryParam.add.class) ActivitySummaryParam activitySummaryParam) {
            activitySummaryService.add(activitySummaryParam);
        return new SuccessResponseData();
    }

    /**
     * 删除活动总结，可批量删除
     *
     * @author jetox
     * @date 2022-06-17 22:55:11
     */
    @Permission
    @PostMapping("/activitySummary/delete")
    @BusinessLog(title = "活动总结_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(ActivitySummaryParam.delete.class) List<ActivitySummaryParam> activitySummaryParamList) {
            activitySummaryService.delete(activitySummaryParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑活动总结
     *
     * @author jetox
     * @date 2022-06-17 22:55:11
     */
    @Permission
    @PostMapping("/activitySummary/edit")
    @BusinessLog(title = "活动总结_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(ActivitySummaryParam.edit.class) ActivitySummaryParam activitySummaryParam) {
            activitySummaryService.edit(activitySummaryParam);
        return new SuccessResponseData();
    }

    /**
     * 查看活动总结
     *
     * @author jetox
     * @date 2022-06-17 22:55:11
     */
    @Permission
    @GetMapping("/activitySummary/detail")
    @BusinessLog(title = "活动总结_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(ActivitySummaryParam.detail.class) ActivitySummaryParam activitySummaryParam) {
        return new SuccessResponseData(activitySummaryService.detail(activitySummaryParam));
    }

    /**
     * 活动总结列表
     *
     * @author jetox
     * @date 2022-06-17 22:55:11
     */
    @Permission
    @GetMapping("/activitySummary/list")
    @BusinessLog(title = "活动总结_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(ActivitySummaryParam activitySummaryParam) {
        return new SuccessResponseData(activitySummaryService.list(activitySummaryParam));
    }

    /**
     * 导出活动总结
     *
     * @author jetox
     * @date 2022-06-17 22:55:11
     */
    @Permission
    @GetMapping("/activitySummary/export")
    @BusinessLog(title = "活动总结_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(ActivitySummaryParam activitySummaryParam) {
        activitySummaryService.export(activitySummaryParam);
    }

}
