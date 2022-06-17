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
package vip.xiaonuo.modular.activity.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.math.BigDecimal;
import java.math.BigDecimal;

/**
 * 活动
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_activity")
public class Activity extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 活动主题
     */
    @Excel(name = "活动主题")
    private String title;

    /**
     * 活动介绍
     */
    @Excel(name = "活动介绍")
    private String content;

    /**
     * 活动封面
     */
    @Excel(name = "活动封面")
    private Long faceImage;

    /**
     * 报名开始时间
     */
    @Excel(name = "报名开始时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    @Excel(name = "报名开始时间")
    private Date preStarttime;

    /**
     * 报名截至时间
     */
    @Excel(name = "报名截至时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    @Excel(name = "报名截至时间")
    private Date preEndtime;

    /**
     * 活动开始时间
     */
    @Excel(name = "活动开始时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    @Excel(name = "活动开始时间")
    private Date starttime;

    /**
     * 活动结束时间
     */
    @Excel(name = "活动结束时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    @Excel(name = "活动结束时间")
    private Date endtime;

    /**
     * 是否收费
     */
    @Excel(name = "是否收费")
    private Integer chargeStatus;

    /**
     * 报名费
     */
    @Excel(name = "报名费")
    private BigDecimal price;

    /**
     * 是否捐助
     */
    @Excel(name = "是否捐助")
    private Integer welfareStatus;

    /**
     * 捐助金额
     */
    @Excel(name = "捐助金额")
    private BigDecimal welfareMoney;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

}
