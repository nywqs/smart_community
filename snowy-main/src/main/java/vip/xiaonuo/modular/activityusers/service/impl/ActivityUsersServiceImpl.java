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
package vip.xiaonuo.modular.activityusers.service.impl;

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
import vip.xiaonuo.modular.activityusers.entity.ActivityUsers;
import vip.xiaonuo.modular.activityusers.enums.ActivityUsersExceptionEnum;
import vip.xiaonuo.modular.activityusers.mapper.ActivityUsersMapper;
import vip.xiaonuo.modular.activityusers.param.ActivityUsersParam;
import vip.xiaonuo.modular.activityusers.service.ActivityUsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 报名记录service接口实现类
 *
 * @author jetox
 * @date 2022-06-17 22:52:54
 */
@Service
public class ActivityUsersServiceImpl extends ServiceImpl<ActivityUsersMapper, ActivityUsers> implements ActivityUsersService {

    @Override
    public PageResult<ActivityUsers> page(ActivityUsersParam activityUsersParam) {
        QueryWrapper<ActivityUsers> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(activityUsersParam)) {

            // 根据活动ID 查询
            if (ObjectUtil.isNotEmpty(activityUsersParam.getActivityId())) {
                queryWrapper.lambda().eq(ActivityUsers::getActivityId, activityUsersParam.getActivityId());
            }
            // 根据参与用户ID 查询
            if (ObjectUtil.isNotEmpty(activityUsersParam.getUserId())) {
                queryWrapper.lambda().eq(ActivityUsers::getUserId, activityUsersParam.getUserId());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(activityUsersParam.getStatus())) {
                queryWrapper.lambda().eq(ActivityUsers::getStatus, activityUsersParam.getStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<ActivityUsers> list(ActivityUsersParam activityUsersParam) {
        return this.list();
    }

    @Override
    public void add(ActivityUsersParam activityUsersParam) {
        ActivityUsers activityUsers = new ActivityUsers();
        BeanUtil.copyProperties(activityUsersParam, activityUsers);
        this.save(activityUsers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ActivityUsersParam> activityUsersParamList) {
        activityUsersParamList.forEach(activityUsersParam -> {
            this.removeById(activityUsersParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ActivityUsersParam activityUsersParam) {
        ActivityUsers activityUsers = this.queryActivityUsers(activityUsersParam);
        BeanUtil.copyProperties(activityUsersParam, activityUsers);
        this.updateById(activityUsers);
    }

    @Override
    public ActivityUsers detail(ActivityUsersParam activityUsersParam) {
        return this.queryActivityUsers(activityUsersParam);
    }

    /**
     * 获取报名记录
     *
     * @author jetox
     * @date 2022-06-17 22:52:54
     */
    private ActivityUsers queryActivityUsers(ActivityUsersParam activityUsersParam) {
        ActivityUsers activityUsers = this.getById(activityUsersParam.getId());
        if (ObjectUtil.isNull(activityUsers)) {
            throw new ServiceException(ActivityUsersExceptionEnum.NOT_EXIST);
        }
        return activityUsers;
    }

    @Override
    public void export(ActivityUsersParam activityUsersParam) {
        List<ActivityUsers> list = this.list(activityUsersParam);
        PoiUtil.exportExcelWithStream("SnowyActivityUsers.xls", ActivityUsers.class, list);
    }

}
