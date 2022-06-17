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
package vip.xiaonuo.modular.village.param;

import vip.xiaonuo.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;

/**
* 小区参数类
 *
 * @author 程永磊
 * @date 2022-06-17 19:49:18
*/
@Data
public class VillageParam extends BaseParam {

    /**
     * 小区地址
     */
    @NotBlank(message = "小区地址不能为空，请检查address参数", groups = {add.class, edit.class})
    private String address;

    /**
     * 绿化面积
     */
    @NotNull(message = "绿化面积不能为空，请检查afforestedMeasure参数", groups = {add.class, edit.class})
    private BigDecimal afforestedMeasure;

    /**
     * 小区面积
     */
    @NotNull(message = "小区面积不能为空，请检查areaMeasure参数", groups = {add.class, edit.class})
    private BigDecimal areaMeasure;

    /**
     * 社区ID
     */
    @NotNull(message = "社区ID不能为空，请检查communityId参数", groups = {add.class, edit.class})
    private Long communityId;

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

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

}
