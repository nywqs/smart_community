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
package vip.xiaonuo.modular.videocategory.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.videocategory.param.VideoCategoryParam;
import vip.xiaonuo.modular.videocategory.service.VideoCategoryService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 视频分类控制器
 *
 * @author jetox
 * @date 2022-06-17 22:13:03
 */
@RestController
public class VideoCategoryController {

    @Resource
    private VideoCategoryService videoCategoryService;

    /**
     * 查询视频分类
     *
     * @author jetox
     * @date 2022-06-17 22:13:03
     */
    @Permission
    @GetMapping("/videoCategory/page")
    @BusinessLog(title = "视频分类_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(VideoCategoryParam videoCategoryParam) {
        return new SuccessResponseData(videoCategoryService.page(videoCategoryParam));
    }

    /**
     * 添加视频分类
     *
     * @author jetox
     * @date 2022-06-17 22:13:03
     */
    @Permission
    @PostMapping("/videoCategory/add")
    @BusinessLog(title = "视频分类_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(VideoCategoryParam.add.class) VideoCategoryParam videoCategoryParam) {
            videoCategoryService.add(videoCategoryParam);
        return new SuccessResponseData();
    }

    /**
     * 删除视频分类，可批量删除
     *
     * @author jetox
     * @date 2022-06-17 22:13:03
     */
    @Permission
    @PostMapping("/videoCategory/delete")
    @BusinessLog(title = "视频分类_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(VideoCategoryParam.delete.class) List<VideoCategoryParam> videoCategoryParamList) {
            videoCategoryService.delete(videoCategoryParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑视频分类
     *
     * @author jetox
     * @date 2022-06-17 22:13:03
     */
    @Permission
    @PostMapping("/videoCategory/edit")
    @BusinessLog(title = "视频分类_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(VideoCategoryParam.edit.class) VideoCategoryParam videoCategoryParam) {
            videoCategoryService.edit(videoCategoryParam);
        return new SuccessResponseData();
    }

    /**
     * 查看视频分类
     *
     * @author jetox
     * @date 2022-06-17 22:13:03
     */
    @Permission
    @GetMapping("/videoCategory/detail")
    @BusinessLog(title = "视频分类_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(VideoCategoryParam.detail.class) VideoCategoryParam videoCategoryParam) {
        return new SuccessResponseData(videoCategoryService.detail(videoCategoryParam));
    }

    /**
     * 视频分类列表
     *
     * @author jetox
     * @date 2022-06-17 22:13:03
     */
    @Permission
    @GetMapping("/videoCategory/list")
    @BusinessLog(title = "视频分类_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(VideoCategoryParam videoCategoryParam) {
        return new SuccessResponseData(videoCategoryService.list(videoCategoryParam));
    }

    /**
     * 导出视频分类
     *
     * @author jetox
     * @date 2022-06-17 22:13:03
     */
    @Permission
    @GetMapping("/videoCategory/export")
    @BusinessLog(title = "视频分类_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(VideoCategoryParam videoCategoryParam) {
        videoCategoryService.export(videoCategoryParam);
    }

}
