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
package vip.xiaonuo.modular.feedback.service.impl;

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
import vip.xiaonuo.modular.feedback.entity.Feedback;
import vip.xiaonuo.modular.feedback.enums.FeedbackExceptionEnum;
import vip.xiaonuo.modular.feedback.mapper.FeedbackMapper;
import vip.xiaonuo.modular.feedback.param.FeedbackParam;
import vip.xiaonuo.modular.feedback.service.FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 建议反馈service接口实现类
 *
 * @author jetox
 * @date 2022-06-17 22:24:58
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    public PageResult<Feedback> page(FeedbackParam feedbackParam) {
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(feedbackParam)) {

            // 根据标题 查询
            if (ObjectUtil.isNotEmpty(feedbackParam.getTitle())) {
                queryWrapper.lambda().eq(Feedback::getTitle, feedbackParam.getTitle());
            }
            // 根据内容 查询
            if (ObjectUtil.isNotEmpty(feedbackParam.getContent())) {
                queryWrapper.lambda().eq(Feedback::getContent, feedbackParam.getContent());
            }
            // 根据类型（字典 1建议 2反馈） 查询
            if (ObjectUtil.isNotEmpty(feedbackParam.getType())) {
                queryWrapper.lambda().eq(Feedback::getType, feedbackParam.getType());
            }
            // 根据满意度 查询
            if (ObjectUtil.isNotEmpty(feedbackParam.getStar())) {
                queryWrapper.lambda().eq(Feedback::getStar, feedbackParam.getStar());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(feedbackParam.getStatus())) {
                queryWrapper.lambda().eq(Feedback::getStatus, feedbackParam.getStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Feedback> list(FeedbackParam feedbackParam) {
        return this.list();
    }

    @Override
    public void add(FeedbackParam feedbackParam) {
        Feedback feedback = new Feedback();
        BeanUtil.copyProperties(feedbackParam, feedback);
        this.save(feedback);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FeedbackParam> feedbackParamList) {
        feedbackParamList.forEach(feedbackParam -> {
            this.removeById(feedbackParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FeedbackParam feedbackParam) {
        Feedback feedback = this.queryFeedback(feedbackParam);
        BeanUtil.copyProperties(feedbackParam, feedback);
        this.updateById(feedback);
    }

    @Override
    public Feedback detail(FeedbackParam feedbackParam) {
        return this.queryFeedback(feedbackParam);
    }

    /**
     * 获取建议反馈
     *
     * @author jetox
     * @date 2022-06-17 22:24:58
     */
    private Feedback queryFeedback(FeedbackParam feedbackParam) {
        Feedback feedback = this.getById(feedbackParam.getId());
        if (ObjectUtil.isNull(feedback)) {
            throw new ServiceException(FeedbackExceptionEnum.NOT_EXIST);
        }
        return feedback;
    }

    @Override
    public void export(FeedbackParam feedbackParam) {
        List<Feedback> list = this.list(feedbackParam);
        PoiUtil.exportExcelWithStream("SnowyFeedback.xls", Feedback.class, list);
    }

}
