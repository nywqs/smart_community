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
package vip.xiaonuo.modular.house.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.house.param.HouseParam;
import vip.xiaonuo.modular.house.service.HouseService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 房屋控制器
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
 */
@RestController
public class HouseController {

    @Resource
    private HouseService houseService;

    /**
     * 查询房屋
     *
     * @author 程永磊
     * @date 2022-06-17 20:21:07
     */
    @Permission
    @GetMapping("/house/page")
    @BusinessLog(title = "房屋_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(HouseParam houseParam) {
        return new SuccessResponseData(houseService.page(houseParam));
    }

    /**
     * 添加房屋
     *
     * @author 程永磊
     * @date 2022-06-17 20:21:07
     */
    @Permission
    @PostMapping("/house/add")
    @BusinessLog(title = "房屋_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(HouseParam.add.class) HouseParam houseParam) {
            houseService.add(houseParam);
        return new SuccessResponseData();
    }

    /**
     * 删除房屋，可批量删除
     *
     * @author 程永磊
     * @date 2022-06-17 20:21:07
     */
    @Permission
    @PostMapping("/house/delete")
    @BusinessLog(title = "房屋_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(HouseParam.delete.class) List<HouseParam> houseParamList) {
            houseService.delete(houseParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑房屋
     *
     * @author 程永磊
     * @date 2022-06-17 20:21:07
     */
    @Permission
    @PostMapping("/house/edit")
    @BusinessLog(title = "房屋_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(HouseParam.edit.class) HouseParam houseParam) {
            houseService.edit(houseParam);
        return new SuccessResponseData();
    }

    /**
     * 查看房屋
     *
     * @author 程永磊
     * @date 2022-06-17 20:21:07
     */
    @Permission
    @GetMapping("/house/detail")
    @BusinessLog(title = "房屋_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(HouseParam.detail.class) HouseParam houseParam) {
        return new SuccessResponseData(houseService.detail(houseParam));
    }

    /**
     * 房屋列表
     *
     * @author 程永磊
     * @date 2022-06-17 20:21:07
     */
    @Permission
    @GetMapping("/house/list")
    @BusinessLog(title = "房屋_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(HouseParam houseParam) {
        return new SuccessResponseData(houseService.list(houseParam));
    }

    /**
     * 导出房屋
     *
     * @author 程永磊
     * @date 2022-06-17 20:21:07
     */
    @Permission
    @GetMapping("/house/export")
    @BusinessLog(title = "房屋_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(HouseParam houseParam) {
        houseService.export(houseParam);
    }

}
