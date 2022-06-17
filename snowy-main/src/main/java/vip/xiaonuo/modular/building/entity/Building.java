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
package vip.xiaonuo.modular.building.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 楼宇
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_building")
public class Building extends BaseEntity {


    /**
     * 小区地址
     */
    @Excel(name = "小区地址")
    private String address;

    /**
     * 建筑时间
     */
    @Excel(name = "建筑时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    @Excel(name = "建筑时间")
    private Date buildDate;

    /**
     * 楼宇编号
     */
    @Excel(name = "楼宇编号")
    private String code;

    /**
     * 社区ID
     */
    @Excel(name = "社区ID")
    private Long communityId;

    /**
     * 总层数
     */
    @Excel(name = "总层数")
    private Integer floorCount;

    /**
     * 户型
     */
    @Excel(name = "户型")
    private Integer floorHouse;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 梯类型
     */
    @Excel(name = "梯类型")
    private Integer ladderType;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String title;

    /**
     * 建筑类型（0:小高层,1:高层）
     */
    @Excel(name = "建筑类型（0:小高层,1:高层）")
    private Integer type;

    /**
     * 建筑性质(0:住宅，1：商用房,3:商住两用)
     */
    @Excel(name = "建筑性质(0:住宅，1：商用房,3:商住两用)")
    private Integer useType;

    /**
     * 小区ID
     */
    @Excel(name = "小区ID")
    private Long villageId;

}
