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
package vip.xiaonuo.modular.village.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.modular.village.entity.Village;
import vip.xiaonuo.modular.village.param.VillageParam;
import java.util.List;

/**
 * 小区service接口
 *
 * @author 程永磊
 * @date 2022-06-17 19:56:45
 */
public interface VillageService extends IService<Village> {

    /**
     * 查询小区
     *
     * @author 程永磊
     * @date 2022-06-17 19:56:45
     */
    PageResult<Village> page(VillageParam villageParam);

    /**
     * 小区列表
     *
     * @author 程永磊
     * @date 2022-06-17 19:56:45
     */
    List<Village> list(VillageParam villageParam);

    /**
     * 添加小区
     *
     * @author 程永磊
     * @date 2022-06-17 19:56:45
     */
    void add(VillageParam villageParam);

    /**
     * 删除小区
     *
     * @author 程永磊
     * @date 2022-06-17 19:56:45
     */
    void delete(List<VillageParam> villageParamList);

    /**
     * 编辑小区
     *
     * @author 程永磊
     * @date 2022-06-17 19:56:45
     */
    void edit(VillageParam villageParam);

    /**
     * 查看小区
     *
     * @author 程永磊
     * @date 2022-06-17 19:56:45
     */
     Village detail(VillageParam villageParam);

    /**
     * 导出小区
     *
     * @author 程永磊
     * @date 2022-06-17 19:56:45
     */
     void export(VillageParam villageParam);

}
