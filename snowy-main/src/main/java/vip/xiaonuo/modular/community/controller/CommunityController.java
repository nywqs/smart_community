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
package vip.xiaonuo.modular.community.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.community.param.CommunityParam;
import vip.xiaonuo.modular.community.service.CommunityService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 社区管理控制器
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
@RestController
public class CommunityController {

    @Resource
    private CommunityService communityService;

    /**
     * 查询社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    @Permission
    @GetMapping("/community/page")
    @BusinessLog(title = "社区管理_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(CommunityParam communityParam) {
        return new SuccessResponseData(communityService.page(communityParam));
    }

    /**
     * 添加社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    @Permission
    @PostMapping("/community/add")
    @BusinessLog(title = "社区管理_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(CommunityParam.add.class) CommunityParam communityParam) {
            communityService.add(communityParam);
        return new SuccessResponseData();
    }

    /**
     * 删除社区管理，可批量删除
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    @Permission
    @PostMapping("/community/delete")
    @BusinessLog(title = "社区管理_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(CommunityParam.delete.class) List<CommunityParam> communityParamList) {
            communityService.delete(communityParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    @Permission
    @PostMapping("/community/edit")
    @BusinessLog(title = "社区管理_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(CommunityParam.edit.class) CommunityParam communityParam) {
            communityService.edit(communityParam);
        return new SuccessResponseData();
    }

    /**
     * 查看社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    @Permission
    @GetMapping("/community/detail")
    @BusinessLog(title = "社区管理_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(CommunityParam.detail.class) CommunityParam communityParam) {
        return new SuccessResponseData(communityService.detail(communityParam));
    }

    /**
     * 社区管理列表
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    @Permission
    @GetMapping("/community/list")
    @BusinessLog(title = "社区管理_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(CommunityParam communityParam) {
        return new SuccessResponseData(communityService.list(communityParam));
    }

    /**
     * 导出社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    @Permission
    @GetMapping("/community/export")
    @BusinessLog(title = "社区管理_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(CommunityParam communityParam) {
        communityService.export(communityParam);
    }

}
