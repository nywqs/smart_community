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
package vip.xiaonuo.modular.building.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.building.param.BuildingParam;
import vip.xiaonuo.modular.building.service.BuildingService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 楼宇控制器
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
@RestController
public class BuildingController {

    @Resource
    private BuildingService buildingService;

    /**
     * 查询楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    @Permission
    @GetMapping("/building/page")
    @BusinessLog(title = "楼宇_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BuildingParam buildingParam) {
        return new SuccessResponseData(buildingService.page(buildingParam));
    }

    /**
     * 添加楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    @Permission
    @PostMapping("/building/add")
    @BusinessLog(title = "楼宇_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BuildingParam.add.class) BuildingParam buildingParam) {
            buildingService.add(buildingParam);
        return new SuccessResponseData();
    }

    /**
     * 删除楼宇，可批量删除
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    @Permission
    @PostMapping("/building/delete")
    @BusinessLog(title = "楼宇_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BuildingParam.delete.class) List<BuildingParam> buildingParamList) {
            buildingService.delete(buildingParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    @Permission
    @PostMapping("/building/edit")
    @BusinessLog(title = "楼宇_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BuildingParam.edit.class) BuildingParam buildingParam) {
            buildingService.edit(buildingParam);
        return new SuccessResponseData();
    }

    /**
     * 查看楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    @Permission
    @GetMapping("/building/detail")
    @BusinessLog(title = "楼宇_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BuildingParam.detail.class) BuildingParam buildingParam) {
        return new SuccessResponseData(buildingService.detail(buildingParam));
    }

    /**
     * 楼宇列表
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    @Permission
    @GetMapping("/building/list")
    @BusinessLog(title = "楼宇_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BuildingParam buildingParam) {
        return new SuccessResponseData(buildingService.list(buildingParam));
    }

    /**
     * 导出楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    @Permission
    @GetMapping("/building/export")
    @BusinessLog(title = "楼宇_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BuildingParam buildingParam) {
        buildingService.export(buildingParam);
    }

}
