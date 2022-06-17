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
package vip.xiaonuo.modular.video.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 视频列表
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_video")
public class Video extends BaseEntity {


    /**
     * 简要描述
     */
    @Excel(name = "简要描述")
    private tinytext content;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 满意度
     */
    @Excel(name = "满意度")
    private Integer playTimes;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 类型（字典 1建议 2反馈）
     */
    @Excel(name = "类型（字典 1建议 2反馈）")
    private Integer type;

    /**
     * 视频地址
     */
    @Excel(name = "视频地址")
    private String url;

}
