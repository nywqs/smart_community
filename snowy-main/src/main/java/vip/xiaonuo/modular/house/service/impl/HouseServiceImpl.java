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
package vip.xiaonuo.modular.house.service.impl;

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
import vip.xiaonuo.modular.house.entity.House;
import vip.xiaonuo.modular.house.enums.HouseExceptionEnum;
import vip.xiaonuo.modular.house.mapper.HouseMapper;
import vip.xiaonuo.modular.house.param.HouseParam;
import vip.xiaonuo.modular.house.service.HouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 房屋service接口实现类
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
 */
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Override
    public PageResult<House> page(HouseParam houseParam) {
        QueryWrapper<House> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(houseParam)) {

            // 根据楼宇ID 查询
            if (ObjectUtil.isNotEmpty(houseParam.getBuildingId())) {
                queryWrapper.lambda().eq(House::getBuildingId, houseParam.getBuildingId());
            }
            // 根据门牌号 查询
            if (ObjectUtil.isNotEmpty(houseParam.getCode())) {
                queryWrapper.lambda().eq(House::getCode, houseParam.getCode());
            }
            // 根据社区ID 查询
            if (ObjectUtil.isNotEmpty(houseParam.getCommunityId())) {
                queryWrapper.lambda().eq(House::getCommunityId, houseParam.getCommunityId());
            }
            // 根据楼层 查询
            if (ObjectUtil.isNotEmpty(houseParam.getFloor())) {
                queryWrapper.lambda().eq(House::getFloor, houseParam.getFloor());
            }
            // 根据户型(例如一房一厅) 查询
            if (ObjectUtil.isNotEmpty(houseParam.getHouseType())) {
                queryWrapper.lambda().eq(House::getHouseType, houseParam.getHouseType());
            }
            // 根据是否出租 查询
            if (ObjectUtil.isNotEmpty(houseParam.getLeaseStatus())) {
                queryWrapper.lambda().eq(House::getLeaseStatus, houseParam.getLeaseStatus());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(houseParam.getStatus())) {
                queryWrapper.lambda().eq(House::getStatus, houseParam.getStatus());
            }
            // 根据名称 查询
            if (ObjectUtil.isNotEmpty(houseParam.getTitle())) {
                queryWrapper.lambda().eq(House::getTitle, houseParam.getTitle());
            }
            // 根据房屋性质(0:居家，1:办公) 查询
            if (ObjectUtil.isNotEmpty(houseParam.getUseType())) {
                queryWrapper.lambda().eq(House::getUseType, houseParam.getUseType());
            }
            // 根据小区ID 查询
            if (ObjectUtil.isNotEmpty(houseParam.getVillageId())) {
                queryWrapper.lambda().eq(House::getVillageId, houseParam.getVillageId());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<House> list(HouseParam houseParam) {
        return this.list();
    }

    @Override
    public void add(HouseParam houseParam) {
        House house = new House();
        BeanUtil.copyProperties(houseParam, house);
        this.save(house);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HouseParam> houseParamList) {
        houseParamList.forEach(houseParam -> {
            this.removeById(houseParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HouseParam houseParam) {
        House house = this.queryHouse(houseParam);
        BeanUtil.copyProperties(houseParam, house);
        this.updateById(house);
    }

    @Override
    public House detail(HouseParam houseParam) {
        return this.queryHouse(houseParam);
    }

    /**
     * 获取房屋
     *
     * @author 程永磊
     * @date 2022-06-17 20:21:07
     */
    private House queryHouse(HouseParam houseParam) {
        House house = this.getById(houseParam.getId());
        if (ObjectUtil.isNull(house)) {
            throw new ServiceException(HouseExceptionEnum.NOT_EXIST);
        }
        return house;
    }

    @Override
    public void export(HouseParam houseParam) {
        List<House> list = this.list(houseParam);
        PoiUtil.exportExcelWithStream("SnowyHouse.xls", House.class, list);
    }

}
