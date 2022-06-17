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
package vip.xiaonuo.modular.village.service.impl;

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
import vip.xiaonuo.modular.village.entity.Village;
import vip.xiaonuo.modular.village.enums.VillageExceptionEnum;
import vip.xiaonuo.modular.village.mapper.VillageMapper;
import vip.xiaonuo.modular.village.param.VillageParam;
import vip.xiaonuo.modular.village.service.VillageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 小区service接口实现类
 *
 * @author 程永磊
 * @date 2022-06-17 19:56:45
 */
@Service
public class VillageServiceImpl extends ServiceImpl<VillageMapper, Village> implements VillageService {

    @Override
    public PageResult<Village> page(VillageParam villageParam) {
        QueryWrapper<Village> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(villageParam)) {

            // 根据小区地址 查询
            if (ObjectUtil.isNotEmpty(villageParam.getAddress())) {
                queryWrapper.lambda().eq(Village::getAddress, villageParam.getAddress());
            }
            // 根据社区ID 查询
            if (ObjectUtil.isNotEmpty(villageParam.getCommunityId())) {
                queryWrapper.lambda().eq(Village::getCommunityId, villageParam.getCommunityId());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(villageParam.getStatus())) {
                queryWrapper.lambda().eq(Village::getStatus, villageParam.getStatus());
            }
            // 根据名称 查询
            if (ObjectUtil.isNotEmpty(villageParam.getTitle())) {
                queryWrapper.lambda().eq(Village::getTitle, villageParam.getTitle());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Village> list(VillageParam villageParam) {
        return this.list();
    }

    @Override
    public void add(VillageParam villageParam) {
        Village village = new Village();
        BeanUtil.copyProperties(villageParam, village);
        this.save(village);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<VillageParam> villageParamList) {
        villageParamList.forEach(villageParam -> {
            this.removeById(villageParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(VillageParam villageParam) {
        Village village = this.queryVillage(villageParam);
        BeanUtil.copyProperties(villageParam, village);
        this.updateById(village);
    }

    @Override
    public Village detail(VillageParam villageParam) {
        return this.queryVillage(villageParam);
    }

    /**
     * 获取小区
     *
     * @author 程永磊
     * @date 2022-06-17 19:56:45
     */
    private Village queryVillage(VillageParam villageParam) {
        Village village = this.getById(villageParam.getId());
        if (ObjectUtil.isNull(village)) {
            throw new ServiceException(VillageExceptionEnum.NOT_EXIST);
        }
        return village;
    }

    @Override
    public void export(VillageParam villageParam) {
        List<Village> list = this.list(villageParam);
        PoiUtil.exportExcelWithStream("SnowyVillage.xls", Village.class, list);
    }

}
