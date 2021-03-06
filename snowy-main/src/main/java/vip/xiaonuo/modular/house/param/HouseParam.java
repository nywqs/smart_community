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
package vip.xiaonuo.modular.house.param;

import vip.xiaonuo.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 房屋参数类
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
*/
@Data
public class HouseParam extends BaseParam {

    /**
     * 楼宇ID
     */
    @NotNull(message = "楼宇ID不能为空，请检查buildingId参数", groups = {add.class, edit.class})
    private Long buildingId;

    /**
     * 门牌号
     */
    @NotBlank(message = "门牌号不能为空，请检查code参数", groups = {add.class, edit.class})
    private String code;

    /**
     * 社区ID
     */
    @NotNull(message = "社区ID不能为空，请检查communityId参数", groups = {add.class, edit.class})
    private Long communityId;

    /**
     * 楼层
     */
    @NotNull(message = "楼层不能为空，请检查floor参数", groups = {add.class, edit.class})
    private Integer floor;

    /**
     * 户型(例如一房一厅)
     */
    @NotNull(message = "户型(例如一房一厅)不能为空，请检查houseType参数", groups = {add.class, edit.class})
    private Integer houseType;

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 是否出租
     */
    @NotNull(message = "是否出租不能为空，请检查leaseStatus参数", groups = {add.class, edit.class})
    private Integer leaseStatus;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空，请检查title参数", groups = {add.class, edit.class})
    private String title;

    /**
     * 房屋性质(0:居家，1:办公)
     */
    @NotNull(message = "房屋性质(0:居家，1:办公)不能为空，请检查useType参数", groups = {add.class, edit.class})
    private Integer useType;

    /**
     * 小区ID
     */
    @NotNull(message = "小区ID不能为空，请检查villageId参数", groups = {add.class, edit.class})
    private Long villageId;

}
