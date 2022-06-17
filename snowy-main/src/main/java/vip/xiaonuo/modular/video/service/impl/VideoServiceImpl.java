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
package vip.xiaonuo.modular.video.service.impl;

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
import vip.xiaonuo.modular.video.entity.Video;
import vip.xiaonuo.modular.video.enums.VideoExceptionEnum;
import vip.xiaonuo.modular.video.mapper.VideoMapper;
import vip.xiaonuo.modular.video.param.VideoParam;
import vip.xiaonuo.modular.video.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 视频列表service接口实现类
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Override
    public PageResult<Video> page(VideoParam videoParam) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(videoParam)) {

            // 根据简要描述 查询
            if (ObjectUtil.isNotEmpty(videoParam.getContent())) {
                queryWrapper.lambda().eq(Video::getContent, videoParam.getContent());
            }
            // 根据满意度 查询
            if (ObjectUtil.isNotEmpty(videoParam.getPlayTimes())) {
                queryWrapper.lambda().eq(Video::getPlayTimes, videoParam.getPlayTimes());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(videoParam.getStatus())) {
                queryWrapper.lambda().eq(Video::getStatus, videoParam.getStatus());
            }
            // 根据标题 查询
            if (ObjectUtil.isNotEmpty(videoParam.getTitle())) {
                queryWrapper.lambda().eq(Video::getTitle, videoParam.getTitle());
            }
            // 根据类型（字典 1建议 2反馈） 查询
            if (ObjectUtil.isNotEmpty(videoParam.getType())) {
                queryWrapper.lambda().eq(Video::getType, videoParam.getType());
            }
            // 根据视频地址 查询
            if (ObjectUtil.isNotEmpty(videoParam.getUrl())) {
                queryWrapper.lambda().eq(Video::getUrl, videoParam.getUrl());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Video> list(VideoParam videoParam) {
        return this.list();
    }

    @Override
    public void add(VideoParam videoParam) {
        Video video = new Video();
        BeanUtil.copyProperties(videoParam, video);
        this.save(video);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<VideoParam> videoParamList) {
        videoParamList.forEach(videoParam -> {
            this.removeById(videoParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(VideoParam videoParam) {
        Video video = this.queryVideo(videoParam);
        BeanUtil.copyProperties(videoParam, video);
        this.updateById(video);
    }

    @Override
    public Video detail(VideoParam videoParam) {
        return this.queryVideo(videoParam);
    }

    /**
     * 获取视频列表
     *
     * @author jetox
     * @date 2022-06-17 22:07:37
     */
    private Video queryVideo(VideoParam videoParam) {
        Video video = this.getById(videoParam.getId());
        if (ObjectUtil.isNull(video)) {
            throw new ServiceException(VideoExceptionEnum.NOT_EXIST);
        }
        return video;
    }

    @Override
    public void export(VideoParam videoParam) {
        List<Video> list = this.list(videoParam);
        PoiUtil.exportExcelWithStream("SnowyVideo.xls", Video.class, list);
    }

}
