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
package vip.xiaonuo.modular.activity.param;

import vip.xiaonuo.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;

/**
* 活动参数类
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
*/
@Data
public class ActivityParam extends BaseParam {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 活动主题
     */
    @NotBlank(message = "活动主题不能为空，请检查title参数", groups = {add.class, edit.class})
    private String title;

    /**
     * 活动介绍
     */
    @NotBlank(message = "活动介绍不能为空，请检查content参数", groups = {add.class, edit.class})
    private String content;

    /**
     * 活动封面
     */
    @NotNull(message = "活动封面不能为空，请检查faceImage参数", groups = {add.class, edit.class})
    private Long faceImage;

    /**
     * 报名开始时间
     */
    @NotNull(message = "报名开始时间不能为空，请检查preStarttime参数", groups = {add.class, edit.class})
    private String preStarttime;

    /**
     * 报名截至时间
     */
    @NotNull(message = "报名截至时间不能为空，请检查preEndtime参数", groups = {add.class, edit.class})
    private String preEndtime;

    /**
     * 活动开始时间
     */
    @NotNull(message = "活动开始时间不能为空，请检查starttime参数", groups = {add.class, edit.class})
    private String starttime;

    /**
     * 活动结束时间
     */
    @NotNull(message = "活动结束时间不能为空，请检查endtime参数", groups = {add.class, edit.class})
    private String endtime;

    /**
     * 是否收费
     */
    @NotNull(message = "是否收费不能为空，请检查chargeStatus参数", groups = {add.class, edit.class})
    private Integer chargeStatus;

    /**
     * 报名费
     */
    @NotNull(message = "报名费不能为空，请检查price参数", groups = {add.class, edit.class})
    private BigDecimal price;

    /**
     * 是否捐助
     */
    @NotNull(message = "是否捐助不能为空，请检查welfareStatus参数", groups = {add.class, edit.class})
    private Integer welfareStatus;

    /**
     * 捐助金额
     */
    @NotNull(message = "捐助金额不能为空，请检查welfareMoney参数", groups = {add.class, edit.class})
    private BigDecimal welfareMoney;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

}
