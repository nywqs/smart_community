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
package vip.xiaonuo.modular.building.param;

import vip.xiaonuo.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 楼宇参数类
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
*/
@Data
public class BuildingParam extends BaseParam {

    /**
     * 小区地址
     */
    @NotBlank(message = "小区地址不能为空，请检查address参数", groups = {add.class, edit.class})
    private String address;

    /**
     * 建筑时间
     */
    @NotNull(message = "建筑时间不能为空，请检查buildDate参数", groups = {add.class, edit.class})
    private String buildDate;

    /**
     * 楼宇编号
     */
    @NotBlank(message = "楼宇编号不能为空，请检查code参数", groups = {add.class, edit.class})
    private String code;

    /**
     * 社区ID
     */
    @NotNull(message = "社区ID不能为空，请检查communityId参数", groups = {add.class, edit.class})
    private Long communityId;

    /**
     * 总层数
     */
    @NotNull(message = "总层数不能为空，请检查floorCount参数", groups = {add.class, edit.class})
    private Integer floorCount;

    /**
     * 户型
     */
    @NotNull(message = "户型不能为空，请检查floorHouse参数", groups = {add.class, edit.class})
    private Integer floorHouse;

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 梯类型
     */
    @NotNull(message = "梯类型不能为空，请检查ladderType参数", groups = {add.class, edit.class})
    private Integer ladderType;

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
     * 建筑类型（0:小高层,1:高层）
     */
    @NotNull(message = "建筑类型（0:小高层,1:高层）不能为空，请检查type参数", groups = {add.class, edit.class})
    private Integer type;

    /**
     * 建筑性质(0:住宅，1：商用房,3:商住两用)
     */
    @NotNull(message = "建筑性质(0:住宅，1：商用房,3:商住两用)不能为空，请检查useType参数", groups = {add.class, edit.class})
    private Integer useType;

    /**
     * 小区ID
     */
    @NotNull(message = "小区ID不能为空，请检查villageId参数", groups = {add.class, edit.class})
    private Long villageId;

}
