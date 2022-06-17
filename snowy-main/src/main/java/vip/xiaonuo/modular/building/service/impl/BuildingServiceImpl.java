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
package vip.xiaonuo.modular.building.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.core.consts.CommonConstant;
import vip.xiaonuo.core.enums.CommonStatusEnum;
import vip.xiaonuo.core.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.modular.building.entity.Building;
import vip.xiaonuo.modular.building.enums.BuildingExceptionEnum;
import vip.xiaonuo.modular.building.mapper.BuildingMapper;
import vip.xiaonuo.modular.building.param.BuildingParam;
import vip.xiaonuo.modular.building.service.BuildingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 楼宇service接口实现类
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {

    @Override
    public PageResult<Building> page(BuildingParam buildingParam) {
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(buildingParam)) {

            // 根据小区地址 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getAddress())) {
                queryWrapper.lambda().eq(Building::getAddress, buildingParam.getAddress());
            }
            // 根据建筑时间 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getBuildDate())) {
                queryWrapper.lambda().eq(Building::getBuildDate, buildingParam.getBuildDate());
            }
            // 根据楼宇编号 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getCode())) {
                queryWrapper.lambda().eq(Building::getCode, buildingParam.getCode());
            }
            // 根据社区ID 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getCommunityId())) {
                queryWrapper.lambda().eq(Building::getCommunityId, buildingParam.getCommunityId());
            }
            // 根据总层数 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getFloorCount())) {
                queryWrapper.lambda().eq(Building::getFloorCount, buildingParam.getFloorCount());
            }
            // 根据户型 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getFloorHouse())) {
                queryWrapper.lambda().eq(Building::getFloorHouse, buildingParam.getFloorHouse());
            }
            // 根据梯类型 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getLadderType())) {
                queryWrapper.lambda().eq(Building::getLadderType, buildingParam.getLadderType());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getStatus())) {
                queryWrapper.lambda().eq(Building::getStatus, buildingParam.getStatus());
            }
            // 根据名称 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getTitle())) {
                queryWrapper.lambda().eq(Building::getTitle, buildingParam.getTitle());
            }
            // 根据建筑类型（0:小高层,1:高层） 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getType())) {
                queryWrapper.lambda().eq(Building::getType, buildingParam.getType());
            }
            // 根据建筑性质(0:住宅，1：商用房,3:商住两用) 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getUseType())) {
                queryWrapper.lambda().eq(Building::getUseType, buildingParam.getUseType());
            }
            // 根据小区ID 查询
            if (ObjectUtil.isNotEmpty(buildingParam.getVillageId())) {
                queryWrapper.lambda().eq(Building::getVillageId, buildingParam.getVillageId());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Building> list(BuildingParam buildingParam) {
        return this.list();
    }

    @Override
    public void add(BuildingParam buildingParam) {
        Building building = new Building();
        BeanUtil.copyProperties(buildingParam, building);
        this.save(building);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BuildingParam> buildingParamList) {
        buildingParamList.forEach(buildingParam -> {
            this.removeById(buildingParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BuildingParam buildingParam) {
        Building building = this.queryBuilding(buildingParam);
        BeanUtil.copyProperties(buildingParam, building);
        this.updateById(building);
    }

    @Override
    public Building detail(BuildingParam buildingParam) {
        return this.queryBuilding(buildingParam);
    }

    /**
     * 获取楼宇
     *
     * @author 程永磊
     * @date 2022-06-17 20:03:07
     */
    private Building queryBuilding(BuildingParam buildingParam) {
        Building building = this.getById(buildingParam.getId());
        if (ObjectUtil.isNull(building)) {
            throw new ServiceException(BuildingExceptionEnum.NOT_EXIST);
        }
        return building;
    }

    @Override
    public void export(BuildingParam buildingParam) {
        List<Building> list = this.list(buildingParam);
        PoiUtil.exportExcelWithStream("SnowyBuilding.xls", Building.class, list);
    }

}
