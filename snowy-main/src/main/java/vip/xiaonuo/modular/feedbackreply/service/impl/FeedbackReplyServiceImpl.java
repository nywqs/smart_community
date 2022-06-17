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
package vip.xiaonuo.modular.feedbackreply.service.impl;

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
import vip.xiaonuo.modular.feedbackreply.entity.FeedbackReply;
import vip.xiaonuo.modular.feedbackreply.enums.FeedbackReplyExceptionEnum;
import vip.xiaonuo.modular.feedbackreply.mapper.FeedbackReplyMapper;
import vip.xiaonuo.modular.feedbackreply.param.FeedbackReplyParam;
import vip.xiaonuo.modular.feedbackreply.service.FeedbackReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 答复记录service接口实现类
 *
 * @author jetox
 * @date 2022-06-17 22:29:45
 */
@Service
public class FeedbackReplyServiceImpl extends ServiceImpl<FeedbackReplyMapper, FeedbackReply> implements FeedbackReplyService {

    @Override
    public PageResult<FeedbackReply> page(FeedbackReplyParam feedbackReplyParam) {
        QueryWrapper<FeedbackReply> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(feedbackReplyParam)) {

            // 根据反馈主题ID 查询
            if (ObjectUtil.isNotEmpty(feedbackReplyParam.getFeedbackId())) {
                queryWrapper.lambda().eq(FeedbackReply::getFeedbackId, feedbackReplyParam.getFeedbackId());
            }
            // 根据答复内容 查询
            if (ObjectUtil.isNotEmpty(feedbackReplyParam.getContent())) {
                queryWrapper.lambda().eq(FeedbackReply::getContent, feedbackReplyParam.getContent());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(feedbackReplyParam.getStatus())) {
                queryWrapper.lambda().eq(FeedbackReply::getStatus, feedbackReplyParam.getStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<FeedbackReply> list(FeedbackReplyParam feedbackReplyParam) {
        return this.list();
    }

    @Override
    public void add(FeedbackReplyParam feedbackReplyParam) {
        FeedbackReply feedbackReply = new FeedbackReply();
        BeanUtil.copyProperties(feedbackReplyParam, feedbackReply);
        this.save(feedbackReply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FeedbackReplyParam> feedbackReplyParamList) {
        feedbackReplyParamList.forEach(feedbackReplyParam -> {
            this.removeById(feedbackReplyParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FeedbackReplyParam feedbackReplyParam) {
        FeedbackReply feedbackReply = this.queryFeedbackReply(feedbackReplyParam);
        BeanUtil.copyProperties(feedbackReplyParam, feedbackReply);
        this.updateById(feedbackReply);
    }

    @Override
    public FeedbackReply detail(FeedbackReplyParam feedbackReplyParam) {
        return this.queryFeedbackReply(feedbackReplyParam);
    }

    /**
     * 获取答复记录
     *
     * @author jetox
     * @date 2022-06-17 22:29:45
     */
    private FeedbackReply queryFeedbackReply(FeedbackReplyParam feedbackReplyParam) {
        FeedbackReply feedbackReply = this.getById(feedbackReplyParam.getId());
        if (ObjectUtil.isNull(feedbackReply)) {
            throw new ServiceException(FeedbackReplyExceptionEnum.NOT_EXIST);
        }
        return feedbackReply;
    }

    @Override
    public void export(FeedbackReplyParam feedbackReplyParam) {
        List<FeedbackReply> list = this.list(feedbackReplyParam);
        PoiUtil.exportExcelWithStream("SnowyFeedbackReply.xls", FeedbackReply.class, list);
    }

}
