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
package vip.xiaonuo.modular.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.math.BigDecimal;

/**
 * 商户
 *
 * @author 程永磊
 * @date 2022-06-17 20:38:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_merchant")
public class Merchant extends BaseEntity {


    /**
     * 经营地址
     */
    @Excel(name = "经营地址")
    private String address;

    /**
     * 经营类型
     */
    @Excel(name = "经营类型")
    private Integer bizType;

    /**
     * 楼宇ID
     */
    @Excel(name = "楼宇ID")
    private Long buildingId;

    /**
     * 社区ID
     */
    @Excel(name = "社区ID")
    private Long communityId;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 店招照片
     */
    @Excel(name = "店招照片")
    private Integer image;

    /**
     * 开业时间
     */
    @Excel(name = "开业时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    @Excel(name = "开业时间")
    private Date openDate;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String tel;

    /**
     * 商户名称
     */
    @Excel(name = "商户名称")
    private String title;

    /**
     * 经营面积
     */
    @Excel(name = "经营面积")
    private BigDecimal useArea;

    /**
     * 小区ID
     */
    @Excel(name = "小区ID")
    private Long villageId;

}
