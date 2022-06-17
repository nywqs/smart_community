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
package vip.xiaonuo.modular.activity.service.impl;

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
import vip.xiaonuo.modular.activity.entity.Activity;
import vip.xiaonuo.modular.activity.enums.ActivityExceptionEnum;
import vip.xiaonuo.modular.activity.mapper.ActivityMapper;
import vip.xiaonuo.modular.activity.param.ActivityParam;
import vip.xiaonuo.modular.activity.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 活动service接口实现类
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Override
    public PageResult<Activity> page(ActivityParam activityParam) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(activityParam)) {

            // 根据活动主题 查询
            if (ObjectUtil.isNotEmpty(activityParam.getTitle())) {
                queryWrapper.lambda().eq(Activity::getTitle, activityParam.getTitle());
            }
            // 根据活动介绍 查询
            if (ObjectUtil.isNotEmpty(activityParam.getContent())) {
                queryWrapper.lambda().eq(Activity::getContent, activityParam.getContent());
            }
            // 根据活动封面 查询
            if (ObjectUtil.isNotEmpty(activityParam.getFaceImage())) {
                queryWrapper.lambda().eq(Activity::getFaceImage, activityParam.getFaceImage());
            }
            // 根据报名开始时间 查询
            if (ObjectUtil.isNotEmpty(activityParam.getPreStarttime())) {
                queryWrapper.lambda().eq(Activity::getPreStarttime, activityParam.getPreStarttime());
            }
            // 根据报名截至时间 查询
            if (ObjectUtil.isNotEmpty(activityParam.getPreEndtime())) {
                queryWrapper.lambda().eq(Activity::getPreEndtime, activityParam.getPreEndtime());
            }
            // 根据活动开始时间 查询
            if (ObjectUtil.isNotEmpty(activityParam.getStarttime())) {
                queryWrapper.lambda().eq(Activity::getStarttime, activityParam.getStarttime());
            }
            // 根据活动结束时间 查询
            if (ObjectUtil.isNotEmpty(activityParam.getEndtime())) {
                queryWrapper.lambda().eq(Activity::getEndtime, activityParam.getEndtime());
            }
            // 根据是否收费 查询
            if (ObjectUtil.isNotEmpty(activityParam.getChargeStatus())) {
                queryWrapper.lambda().eq(Activity::getChargeStatus, activityParam.getChargeStatus());
            }
            // 根据报名费 查询
            if (ObjectUtil.isNotEmpty(activityParam.getPrice())) {
                queryWrapper.lambda().eq(Activity::getPrice, activityParam.getPrice());
            }
            // 根据是否捐助 查询
            if (ObjectUtil.isNotEmpty(activityParam.getWelfareStatus())) {
                queryWrapper.lambda().eq(Activity::getWelfareStatus, activityParam.getWelfareStatus());
            }
            // 根据捐助金额 查询
            if (ObjectUtil.isNotEmpty(activityParam.getWelfareMoney())) {
                queryWrapper.lambda().eq(Activity::getWelfareMoney, activityParam.getWelfareMoney());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(activityParam.getStatus())) {
                queryWrapper.lambda().eq(Activity::getStatus, activityParam.getStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Activity> list(ActivityParam activityParam) {
        return this.list();
    }

    @Override
    public void add(ActivityParam activityParam) {
        Activity activity = new Activity();
        BeanUtil.copyProperties(activityParam, activity);
        this.save(activity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ActivityParam> activityParamList) {
        activityParamList.forEach(activityParam -> {
            this.removeById(activityParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ActivityParam activityParam) {
        Activity activity = this.queryActivity(activityParam);
        BeanUtil.copyProperties(activityParam, activity);
        this.updateById(activity);
    }

    @Override
    public Activity detail(ActivityParam activityParam) {
        return this.queryActivity(activityParam);
    }

    /**
     * 获取活动
     *
     * @author jetox
     * @date 2022-06-17 22:43:04
     */
    private Activity queryActivity(ActivityParam activityParam) {
        Activity activity = this.getById(activityParam.getId());
        if (ObjectUtil.isNull(activity)) {
            throw new ServiceException(ActivityExceptionEnum.NOT_EXIST);
        }
        return activity;
    }

    @Override
    public void export(ActivityParam activityParam) {
        List<Activity> list = this.list(activityParam);
        PoiUtil.exportExcelWithStream("SnowyActivity.xls", Activity.class, list);
    }

}
