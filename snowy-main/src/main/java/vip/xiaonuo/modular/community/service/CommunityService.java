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
package vip.xiaonuo.modular.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.modular.community.entity.Community;
import vip.xiaonuo.modular.community.param.CommunityParam;
import java.util.List;

/**
 * 社区管理service接口
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
public interface CommunityService extends IService<Community> {

    /**
     * 查询社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    PageResult<Community> page(CommunityParam communityParam);

    /**
     * 社区管理列表
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    List<Community> list(CommunityParam communityParam);

    /**
     * 添加社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    void add(CommunityParam communityParam);

    /**
     * 删除社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    void delete(List<CommunityParam> communityParamList);

    /**
     * 编辑社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    void edit(CommunityParam communityParam);

    /**
     * 查看社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
     Community detail(CommunityParam communityParam);

    /**
     * 导出社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
     void export(CommunityParam communityParam);

}
