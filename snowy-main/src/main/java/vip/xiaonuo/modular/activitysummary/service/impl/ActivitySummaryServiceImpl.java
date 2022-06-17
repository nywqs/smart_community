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
package vip.xiaonuo.modular.activitysummary.service.impl;

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
import vip.xiaonuo.modular.activitysummary.entity.ActivitySummary;
import vip.xiaonuo.modular.activitysummary.enums.ActivitySummaryExceptionEnum;
import vip.xiaonuo.modular.activitysummary.mapper.ActivitySummaryMapper;
import vip.xiaonuo.modular.activitysummary.param.ActivitySummaryParam;
import vip.xiaonuo.modular.activitysummary.service.ActivitySummaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 活动总结service接口实现类
 *
 * @author jetox
 * @date 2022-06-17 22:55:11
 */
@Service
public class ActivitySummaryServiceImpl extends ServiceImpl<ActivitySummaryMapper, ActivitySummary> implements ActivitySummaryService {

    @Override
    public PageResult<ActivitySummary> page(ActivitySummaryParam activitySummaryParam) {
        QueryWrapper<ActivitySummary> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(activitySummaryParam)) {

            // 根据活动ID 查询
            if (ObjectUtil.isNotEmpty(activitySummaryParam.getActivityId())) {
                queryWrapper.lambda().eq(ActivitySummary::getActivityId, activitySummaryParam.getActivityId());
            }
            // 根据活动照片 查询
            if (ObjectUtil.isNotEmpty(activitySummaryParam.getImages())) {
                queryWrapper.lambda().eq(ActivitySummary::getImages, activitySummaryParam.getImages());
            }
            // 根据总结内容 查询
            if (ObjectUtil.isNotEmpty(activitySummaryParam.getContent())) {
                queryWrapper.lambda().eq(ActivitySummary::getContent, activitySummaryParam.getContent());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(activitySummaryParam.getStatus())) {
                queryWrapper.lambda().eq(ActivitySummary::getStatus, activitySummaryParam.getStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<ActivitySummary> list(ActivitySummaryParam activitySummaryParam) {
        return this.list();
    }

    @Override
    public void add(ActivitySummaryParam activitySummaryParam) {
        ActivitySummary activitySummary = new ActivitySummary();
        BeanUtil.copyProperties(activitySummaryParam, activitySummary);
        this.save(activitySummary);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ActivitySummaryParam> activitySummaryParamList) {
        activitySummaryParamList.forEach(activitySummaryParam -> {
            this.removeById(activitySummaryParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ActivitySummaryParam activitySummaryParam) {
        ActivitySummary activitySummary = this.queryActivitySummary(activitySummaryParam);
        BeanUtil.copyProperties(activitySummaryParam, activitySummary);
        this.updateById(activitySummary);
    }

    @Override
    public ActivitySummary detail(ActivitySummaryParam activitySummaryParam) {
        return this.queryActivitySummary(activitySummaryParam);
    }

    /**
     * 获取活动总结
     *
     * @author jetox
     * @date 2022-06-17 22:55:11
     */
    private ActivitySummary queryActivitySummary(ActivitySummaryParam activitySummaryParam) {
        ActivitySummary activitySummary = this.getById(activitySummaryParam.getId());
        if (ObjectUtil.isNull(activitySummary)) {
            throw new ServiceException(ActivitySummaryExceptionEnum.NOT_EXIST);
        }
        return activitySummary;
    }

    @Override
    public void export(ActivitySummaryParam activitySummaryParam) {
        List<ActivitySummary> list = this.list(activitySummaryParam);
        PoiUtil.exportExcelWithStream("SnowyActivitySummary.xls", ActivitySummary.class, list);
    }

}
