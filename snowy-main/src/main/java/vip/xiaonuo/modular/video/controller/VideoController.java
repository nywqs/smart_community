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
package vip.xiaonuo.modular.video.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.video.param.VideoParam;
import vip.xiaonuo.modular.video.service.VideoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 视频列表控制器
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
@RestController
public class VideoController {

    @Resource
    private VideoService videoService;

    /**
     * 查询视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    @Permission
    @GetMapping("/video/page")
    @BusinessLog(title = "视频列表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(VideoParam videoParam) {
        return new SuccessResponseData(videoService.page(videoParam));
    }

    /**
     * 添加视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    @Permission
    @PostMapping("/video/add")
    @BusinessLog(title = "视频列表_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(VideoParam.add.class) VideoParam videoParam) {
            videoService.add(videoParam);
        return new SuccessResponseData();
    }

    /**
     * 删除视频列表，可批量删除
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    @Permission
    @PostMapping("/video/delete")
    @BusinessLog(title = "视频列表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(VideoParam.delete.class) List<VideoParam> videoParamList) {
            videoService.delete(videoParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    @Permission
    @PostMapping("/video/edit")
    @BusinessLog(title = "视频列表_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(VideoParam.edit.class) VideoParam videoParam) {
            videoService.edit(videoParam);
        return new SuccessResponseData();
    }

    /**
     * 查看视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    @Permission
    @GetMapping("/video/detail")
    @BusinessLog(title = "视频列表_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(VideoParam.detail.class) VideoParam videoParam) {
        return new SuccessResponseData(videoService.detail(videoParam));
    }

    /**
     * 视频列表列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    @Permission
    @GetMapping("/video/list")
    @BusinessLog(title = "视频列表_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(VideoParam videoParam) {
        return new SuccessResponseData(videoService.list(videoParam));
    }

    /**
     * 导出视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    @Permission
    @GetMapping("/video/export")
    @BusinessLog(title = "视频列表_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(VideoParam videoParam) {
        videoService.export(videoParam);
    }

}
