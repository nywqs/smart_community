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
package vip.xiaonuo.modular.feedbackreply.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.feedbackreply.param.FeedbackReplyParam;
import vip.xiaonuo.modular.feedbackreply.service.FeedbackReplyService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 答复记录控制器
 *
 * @author jetox
 * @date 2022-06-17 22:29:45
 */
@RestController
public class FeedbackReplyController {

    @Resource
    private FeedbackReplyService feedbackReplyService;

    /**
     * 查询答复记录
     *
     * @author jetox
     * @date 2022-06-17 22:29:45
     */
    @Permission
    @GetMapping("/feedbackReply/page")
    @BusinessLog(title = "答复记录_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(FeedbackReplyParam feedbackReplyParam) {
        return new SuccessResponseData(feedbackReplyService.page(feedbackReplyParam));
    }

    /**
     * 添加答复记录
     *
     * @author jetox
     * @date 2022-06-17 22:29:45
     */
    @Permission
    @PostMapping("/feedbackReply/add")
    @BusinessLog(title = "答复记录_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(FeedbackReplyParam.add.class) FeedbackReplyParam feedbackReplyParam) {
            feedbackReplyService.add(feedbackReplyParam);
        return new SuccessResponseData();
    }

    /**
     * 删除答复记录，可批量删除
     *
     * @author jetox
     * @date 2022-06-17 22:29:45
     */
    @Permission
    @PostMapping("/feedbackReply/delete")
    @BusinessLog(title = "答复记录_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(FeedbackReplyParam.delete.class) List<FeedbackReplyParam> feedbackReplyParamList) {
            feedbackReplyService.delete(feedbackReplyParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑答复记录
     *
     * @author jetox
     * @date 2022-06-17 22:29:45
     */
    @Permission
    @PostMapping("/feedbackReply/edit")
    @BusinessLog(title = "答复记录_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(FeedbackReplyParam.edit.class) FeedbackReplyParam feedbackReplyParam) {
            feedbackReplyService.edit(feedbackReplyParam);
        return new SuccessResponseData();
    }

    /**
     * 查看答复记录
     *
     * @author jetox
     * @date 2022-06-17 22:29:45
     */
    @Permission
    @GetMapping("/feedbackReply/detail")
    @BusinessLog(title = "答复记录_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(FeedbackReplyParam.detail.class) FeedbackReplyParam feedbackReplyParam) {
        return new SuccessResponseData(feedbackReplyService.detail(feedbackReplyParam));
    }

    /**
     * 答复记录列表
     *
     * @author jetox
     * @date 2022-06-17 22:29:45
     */
    @Permission
    @GetMapping("/feedbackReply/list")
    @BusinessLog(title = "答复记录_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(FeedbackReplyParam feedbackReplyParam) {
        return new SuccessResponseData(feedbackReplyService.list(feedbackReplyParam));
    }

    /**
     * 导出答复记录
     *
     * @author jetox
     * @date 2022-06-17 22:29:45
     */
    @Permission
    @GetMapping("/feedbackReply/export")
    @BusinessLog(title = "答复记录_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(FeedbackReplyParam feedbackReplyParam) {
        feedbackReplyService.export(feedbackReplyParam);
    }

}
