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
package vip.xiaonuo.modular.community.service.impl;

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
import vip.xiaonuo.modular.community.entity.Community;
import vip.xiaonuo.modular.community.enums.CommunityExceptionEnum;
import vip.xiaonuo.modular.community.mapper.CommunityMapper;
import vip.xiaonuo.modular.community.param.CommunityParam;
import vip.xiaonuo.modular.community.service.CommunityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 社区管理service接口实现类
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Override
    public PageResult<Community> page(CommunityParam communityParam) {
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(communityParam)) {

            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(communityParam.getStatus())) {
                queryWrapper.lambda().eq(Community::getStatus, communityParam.getStatus());
            }
            // 根据办公电话 查询
            if (ObjectUtil.isNotEmpty(communityParam.getTel())) {
                queryWrapper.lambda().eq(Community::getTel, communityParam.getTel());
            }
            // 根据名称 查询
            if (ObjectUtil.isNotEmpty(communityParam.getTitle())) {
                queryWrapper.lambda().eq(Community::getTitle, communityParam.getTitle());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Community> list(CommunityParam communityParam) {
        return this.list();
    }

    @Override
    public void add(CommunityParam communityParam) {
        Community community = new Community();
        BeanUtil.copyProperties(communityParam, community);
        this.save(community);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CommunityParam> communityParamList) {
        communityParamList.forEach(communityParam -> {
            this.removeById(communityParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CommunityParam communityParam) {
        Community community = this.queryCommunity(communityParam);
        BeanUtil.copyProperties(communityParam, community);
        this.updateById(community);
    }

    @Override
    public Community detail(CommunityParam communityParam) {
        return this.queryCommunity(communityParam);
    }

    /**
     * 获取社区管理
     *
     * @author 程永磊
     * @date 2022-06-17 18:54:52
     */
    private Community queryCommunity(CommunityParam communityParam) {
        Community community = this.getById(communityParam.getId());
        if (ObjectUtil.isNull(community)) {
            throw new ServiceException(CommunityExceptionEnum.NOT_EXIST);
        }
        return community;
    }

    @Override
    public void export(CommunityParam communityParam) {
        List<Community> list = this.list(communityParam);
        PoiUtil.exportExcelWithStream("SnowyCommunity.xls", Community.class, list);
    }

}
