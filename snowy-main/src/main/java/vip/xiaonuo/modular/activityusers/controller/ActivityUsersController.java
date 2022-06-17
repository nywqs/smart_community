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
package vip.xiaonuo.modular.activityusers.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.activityusers.param.ActivityUsersParam;
import vip.xiaonuo.modular.activityusers.service.ActivityUsersService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 报名记录控制器
 *
 * @author jetox
 * @date 2022-06-17 22:52:54
 */
@RestController
public class ActivityUsersController {

    @Resource
    private ActivityUsersService activityUsersService;

    /**
     * 查询报名记录
     *
     * @author jetox
     * @date 2022-06-17 22:52:54
     */
    @Permission
    @GetMapping("/activityUsers/page")
    @BusinessLog(title = "报名记录_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(ActivityUsersParam activityUsersParam) {
        return new SuccessResponseData(activityUsersService.page(activityUsersParam));
    }

    /**
     * 添加报名记录
     *
     * @author jetox
     * @date 2022-06-17 22:52:54
     */
    @Permission
    @PostMapping("/activityUsers/add")
    @BusinessLog(title = "报名记录_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(ActivityUsersParam.add.class) ActivityUsersParam activityUsersParam) {
            activityUsersService.add(activityUsersParam);
        return new SuccessResponseData();
    }

    /**
     * 删除报名记录，可批量删除
     *
     * @author jetox
     * @date 2022-06-17 22:52:54
     */
    @Permission
    @PostMapping("/activityUsers/delete")
    @BusinessLog(title = "报名记录_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(ActivityUsersParam.delete.class) List<ActivityUsersParam> activityUsersParamList) {
            activityUsersService.delete(activityUsersParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑报名记录
     *
     * @author jetox
     * @date 2022-06-17 22:52:54
     */
    @Permission
    @PostMapping("/activityUsers/edit")
    @BusinessLog(title = "报名记录_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(ActivityUsersParam.edit.class) ActivityUsersParam activityUsersParam) {
            activityUsersService.edit(activityUsersParam);
        return new SuccessResponseData();
    }

    /**
     * 查看报名记录
     *
     * @author jetox
     * @date 2022-06-17 22:52:54
     */
    @Permission
    @GetMapping("/activityUsers/detail")
    @BusinessLog(title = "报名记录_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(ActivityUsersParam.detail.class) ActivityUsersParam activityUsersParam) {
        return new SuccessResponseData(activityUsersService.detail(activityUsersParam));
    }

    /**
     * 报名记录列表
     *
     * @author jetox
     * @date 2022-06-17 22:52:54
     */
    @Permission
    @GetMapping("/activityUsers/list")
    @BusinessLog(title = "报名记录_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(ActivityUsersParam activityUsersParam) {
        return new SuccessResponseData(activityUsersService.list(activityUsersParam));
    }

    /**
     * 导出报名记录
     *
     * @author jetox
     * @date 2022-06-17 22:52:54
     */
    @Permission
    @GetMapping("/activityUsers/export")
    @BusinessLog(title = "报名记录_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(ActivityUsersParam activityUsersParam) {
        activityUsersService.export(activityUsersParam);
    }

}
