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
package vip.xiaonuo.modular.activity.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.activity.param.ActivityParam;
import vip.xiaonuo.modular.activity.service.ActivityService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 活动控制器
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
@RestController
public class ActivityController {

    @Resource
    private ActivityService activityService;

    /**
     * 查询活动
     *
     * @author jetox
     * @date 2022-06-17 22:43:04
     */
    @Permission
    @GetMapping("/activity/page")
    @BusinessLog(title = "活动_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(ActivityParam activityParam) {
        return new SuccessResponseData(activityService.page(activityParam));
    }

    /**
     * 添加活动
     *
     * @author jetox
     * @date 2022-06-17 22:43:04
     */
    @Permission
    @PostMapping("/activity/add")
    @BusinessLog(title = "活动_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(ActivityParam.add.class) ActivityParam activityParam) {
            activityService.add(activityParam);
        return new SuccessResponseData();
    }

    /**
     * 删除活动，可批量删除
     *
     * @author jetox
     * @date 2022-06-17 22:43:04
     */
    @Permission
    @PostMapping("/activity/delete")
    @BusinessLog(title = "活动_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(ActivityParam.delete.class) List<ActivityParam> activityParamList) {
            activityService.delete(activityParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑活动
     *
     * @author jetox
     * @date 2022-06-17 22:43:04
     */
    @Permission
    @PostMapping("/activity/edit")
    @BusinessLog(title = "活动_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(ActivityParam.edit.class) ActivityParam activityParam) {
            activityService.edit(activityParam);
        return new SuccessResponseData();
    }

    /**
     * 查看活动
     *
     * @author jetox
     * @date 2022-06-17 22:43:04
     */
    @Permission
    @GetMapping("/activity/detail")
    @BusinessLog(title = "活动_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(ActivityParam.detail.class) ActivityParam activityParam) {
        return new SuccessResponseData(activityService.detail(activityParam));
    }

    /**
     * 活动列表
     *
     * @author jetox
     * @date 2022-06-17 22:43:04
     */
    @Permission
    @GetMapping("/activity/list")
    @BusinessLog(title = "活动_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(ActivityParam activityParam) {
        return new SuccessResponseData(activityService.list(activityParam));
    }

    /**
     * 导出活动
     *
     * @author jetox
     * @date 2022-06-17 22:43:04
     */
    @Permission
    @GetMapping("/activity/export")
    @BusinessLog(title = "活动_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(ActivityParam activityParam) {
        activityService.export(activityParam);
    }

}
