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
package vip.xiaonuo.modular.videocategory.service.impl;

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
import vip.xiaonuo.modular.videocategory.entity.VideoCategory;
import vip.xiaonuo.modular.videocategory.enums.VideoCategoryExceptionEnum;
import vip.xiaonuo.modular.videocategory.mapper.VideoCategoryMapper;
import vip.xiaonuo.modular.videocategory.param.VideoCategoryParam;
import vip.xiaonuo.modular.videocategory.service.VideoCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 视频分类service接口实现类
 *
 * @author jetox
 * @date 2022-06-17 22:13:03
 */
@Service
public class VideoCategoryServiceImpl extends ServiceImpl<VideoCategoryMapper, VideoCategory> implements VideoCategoryService {

    @Override
    public PageResult<VideoCategory> page(VideoCategoryParam videoCategoryParam) {
        QueryWrapper<VideoCategory> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(videoCategoryParam)) {

            // 根据分类名称 查询
            if (ObjectUtil.isNotEmpty(videoCategoryParam.getName())) {
                queryWrapper.lambda().eq(VideoCategory::getName, videoCategoryParam.getName());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(videoCategoryParam.getStatus())) {
                queryWrapper.lambda().eq(VideoCategory::getStatus, videoCategoryParam.getStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<VideoCategory> list(VideoCategoryParam videoCategoryParam) {
        return this.list();
    }

    @Override
    public void add(VideoCategoryParam videoCategoryParam) {
        VideoCategory videoCategory = new VideoCategory();
        BeanUtil.copyProperties(videoCategoryParam, videoCategory);
        this.save(videoCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<VideoCategoryParam> videoCategoryParamList) {
        videoCategoryParamList.forEach(videoCategoryParam -> {
            this.removeById(videoCategoryParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(VideoCategoryParam videoCategoryParam) {
        VideoCategory videoCategory = this.queryVideoCategory(videoCategoryParam);
        BeanUtil.copyProperties(videoCategoryParam, videoCategory);
        this.updateById(videoCategory);
    }

    @Override
    public VideoCategory detail(VideoCategoryParam videoCategoryParam) {
        return this.queryVideoCategory(videoCategoryParam);
    }

    /**
     * 获取视频分类
     *
     * @author jetox
     * @date 2022-06-17 22:13:03
     */
    private VideoCategory queryVideoCategory(VideoCategoryParam videoCategoryParam) {
        VideoCategory videoCategory = this.getById(videoCategoryParam.getId());
        if (ObjectUtil.isNull(videoCategory)) {
            throw new ServiceException(VideoCategoryExceptionEnum.NOT_EXIST);
        }
        return videoCategory;
    }

    @Override
    public void export(VideoCategoryParam videoCategoryParam) {
        List<VideoCategory> list = this.list(videoCategoryParam);
        PoiUtil.exportExcelWithStream("SnowyVideoCategory.xls", VideoCategory.class, list);
    }

}
