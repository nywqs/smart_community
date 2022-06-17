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
package vip.xiaonuo.modular.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.modular.video.entity.Video;
import vip.xiaonuo.modular.video.param.VideoParam;
import java.util.List;

/**
 * 视频列表service接口
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
public interface VideoService extends IService<Video> {

    /**
     * 查询视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    PageResult<Video> page(VideoParam videoParam);

    /**
     * 视频列表列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    List<Video> list(VideoParam videoParam);

    /**
     * 添加视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    void add(VideoParam videoParam);

    /**
     * 删除视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    void delete(List<VideoParam> videoParamList);

    /**
     * 编辑视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    void edit(VideoParam videoParam);

    /**
     * 查看视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
     Video detail(VideoParam videoParam);

    /**
     * 导出视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
     void export(VideoParam videoParam);

}
