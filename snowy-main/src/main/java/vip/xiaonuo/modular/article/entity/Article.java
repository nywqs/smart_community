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
package vip.xiaonuo.modular.article.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 文章
 *
 * @author 程永磊
 * @date 2022-06-17 12:25:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article")
public class Article extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private Integer type;

    /**
     * 置顶
     */
    @Excel(name = "置顶")
    private String top;

    /**
     * 热门
     */
    @Excel(name = "热门")
    private String hot;

    /**
     * 发布人id
     */
    @Excel(name = "发布人id")
    private Long publicUserId;

    /**
     * 发布人姓名
     */
    @Excel(name = "发布人姓名")
    private String publicUserName;

    /**
     * 发布机构id
     */
    @Excel(name = "发布机构id")
    private Long publicOrgId;

    /**
     * 发布机构名称
     */
    @Excel(name = "发布机构名称")
    private String publicOrgName;

    /**
     * 发布时间
     */
    private Date publicTime;

    /**
     * 撤回时间
     */
    private Date cancelTime;

    /**
     * 状态
     */
    private Integer status;

}
