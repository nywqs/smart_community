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
package vip.xiaonuo.modular.building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.modular.building.entity.Building;
import vip.xiaonuo.modular.building.param.BuildingParam;
import java.util.List;

/**
 * 楼宇service接口
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
public interface BuildingService extends IService<Building> {

    /**
     * 查询楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    PageResult<Building> page(BuildingParam buildingParam);

    /**
     * 楼宇列表
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    List<Building> list(BuildingParam buildingParam);

    /**
     * 添加楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    void add(BuildingParam buildingParam);

    /**
     * 删除楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    void delete(List<BuildingParam> buildingParamList);

    /**
     * 编辑楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    void edit(BuildingParam buildingParam);

    /**
     * 查看楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
     Building detail(BuildingParam buildingParam);

    /**
     * 导出楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
     void export(BuildingParam buildingParam);

}
