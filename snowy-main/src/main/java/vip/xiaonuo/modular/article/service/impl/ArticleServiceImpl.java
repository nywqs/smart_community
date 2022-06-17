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
package vip.xiaonuo.modular.article.service.impl;

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
import vip.xiaonuo.modular.article.entity.Article;
import vip.xiaonuo.modular.article.enums.ArticleExceptionEnum;
import vip.xiaonuo.modular.article.mapper.ArticleMapper;
import vip.xiaonuo.modular.article.param.ArticleParam;
import vip.xiaonuo.modular.article.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 文章service接口实现类
 *
 * @author 程永磊
 * @date 2022-06-17 12:25:39
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public PageResult<Article> page(ArticleParam articleParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(articleParam)) {

            // 根据标题 查询
            if (ObjectUtil.isNotEmpty(articleParam.getTitle())) {
                queryWrapper.lambda().like(Article::getTitle, articleParam.getTitle());
            }
            // 根据内容 查询
            if (ObjectUtil.isNotEmpty(articleParam.getContent())) {
                queryWrapper.lambda().like(Article::getContent, articleParam.getContent());
            }
            // 根据类型 查询
            if (ObjectUtil.isNotEmpty(articleParam.getType())) {
                queryWrapper.lambda().eq(Article::getType, articleParam.getType());
            }
            // 根据置顶 查询
            if (ObjectUtil.isNotEmpty(articleParam.getTop())) {
                queryWrapper.lambda().eq(Article::getTop, articleParam.getTop());
            }
            // 根据热门 查询
            if (ObjectUtil.isNotEmpty(articleParam.getHot())) {
                queryWrapper.lambda().eq(Article::getHot, articleParam.getHot());
            }
        }
        queryWrapper.orderByDesc("create_time");
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Article> list(ArticleParam articleParam) {
        return this.list();
    }

    @Override
    public void add(ArticleParam articleParam) {
        Article article = new Article();
        BeanUtil.copyProperties(articleParam, article);
        this.save(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ArticleParam> articleParamList) {
        articleParamList.forEach(articleParam -> {
            this.removeById(articleParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ArticleParam articleParam) {
        Article article = this.queryArticle(articleParam);
        BeanUtil.copyProperties(articleParam, article);
        this.updateById(article);
    }

    @Override
    public Article detail(ArticleParam articleParam) {
        return this.queryArticle(articleParam);
    }

    /**
     * 获取文章
     *
     * @author 程永磊
     * @date 2022-06-17 12:25:39
     */
    private Article queryArticle(ArticleParam articleParam) {
        Article article = this.getById(articleParam.getId());
        if (ObjectUtil.isNull(article)) {
            throw new ServiceException(ArticleExceptionEnum.NOT_EXIST);
        }
        return article;
    }

    @Override
    public void export(ArticleParam articleParam) {
        List<Article> list = this.list(articleParam);
        PoiUtil.exportExcelWithStream("SnowyArticle.xls", Article.class, list);
    }

}
