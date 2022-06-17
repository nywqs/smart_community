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
package vip.xiaonuo.modular.merchant.service.impl;

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
import vip.xiaonuo.modular.merchant.entity.Merchant;
import vip.xiaonuo.modular.merchant.enums.MerchantExceptionEnum;
import vip.xiaonuo.modular.merchant.mapper.MerchantMapper;
import vip.xiaonuo.modular.merchant.param.MerchantParam;
import vip.xiaonuo.modular.merchant.service.MerchantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 商户service接口实现类
 *
 * @author 程永磊
 * @date 2022-06-17 20:38:52
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService {

    @Override
    public PageResult<Merchant> page(MerchantParam merchantParam) {
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(merchantParam)) {

            // 根据经营地址 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getAddress())) {
                queryWrapper.lambda().eq(Merchant::getAddress, merchantParam.getAddress());
            }
            // 根据经营类型 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getBizType())) {
                queryWrapper.lambda().eq(Merchant::getBizType, merchantParam.getBizType());
            }
            // 根据楼宇ID 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getBuildingId())) {
                queryWrapper.lambda().eq(Merchant::getBuildingId, merchantParam.getBuildingId());
            }
            // 根据社区ID 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getCommunityId())) {
                queryWrapper.lambda().eq(Merchant::getCommunityId, merchantParam.getCommunityId());
            }
            // 根据店招照片 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getImage())) {
                queryWrapper.lambda().eq(Merchant::getImage, merchantParam.getImage());
            }
            // 根据开业时间 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getOpenDate())) {
                queryWrapper.lambda().eq(Merchant::getOpenDate, merchantParam.getOpenDate());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getStatus())) {
                queryWrapper.lambda().eq(Merchant::getStatus, merchantParam.getStatus());
            }
            // 根据联系电话 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getTel())) {
                queryWrapper.lambda().eq(Merchant::getTel, merchantParam.getTel());
            }
            // 根据商户名称 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getTitle())) {
                queryWrapper.lambda().eq(Merchant::getTitle, merchantParam.getTitle());
            }
            // 根据经营面积 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getUseArea())) {
                queryWrapper.lambda().eq(Merchant::getUseArea, merchantParam.getUseArea());
            }
            // 根据小区ID 查询
            if (ObjectUtil.isNotEmpty(merchantParam.getVillageId())) {
                queryWrapper.lambda().eq(Merchant::getVillageId, merchantParam.getVillageId());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Merchant> list(MerchantParam merchantParam) {
        return this.list();
    }

    @Override
    public void add(MerchantParam merchantParam) {
        Merchant merchant = new Merchant();
        BeanUtil.copyProperties(merchantParam, merchant);
        this.save(merchant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<MerchantParam> merchantParamList) {
        merchantParamList.forEach(merchantParam -> {
            this.removeById(merchantParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(MerchantParam merchantParam) {
        Merchant merchant = this.queryMerchant(merchantParam);
        BeanUtil.copyProperties(merchantParam, merchant);
        this.updateById(merchant);
    }

    @Override
    public Merchant detail(MerchantParam merchantParam) {
        return this.queryMerchant(merchantParam);
    }

    /**
     * 获取商户
     *
     * @author 程永磊
     * @date 2022-06-17 20:38:52
     */
    private Merchant queryMerchant(MerchantParam merchantParam) {
        Merchant merchant = this.getById(merchantParam.getId());
        if (ObjectUtil.isNull(merchant)) {
            throw new ServiceException(MerchantExceptionEnum.NOT_EXIST);
        }
        return merchant;
    }

    @Override
    public void export(MerchantParam merchantParam) {
        List<Merchant> list = this.list(merchantParam);
        PoiUtil.exportExcelWithStream("SnowyMerchant.xls", Merchant.class, list);
    }

}
