package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.constant.CommonConstant;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.domain.search.SearchArticleDTO;
import com.iris.blog.domain.search.app.AppSearchAppArticleDTO;
import com.iris.blog.domain.vo.SessionUserVO;
import com.iris.blog.domain.vo.SystemHomeContributeStatisticsVO;
import com.iris.blog.domain.vo.app.*;
import com.iris.blog.service.ArticleCategoryService;
import com.iris.blog.service.ArticleTagService;
import com.iris.blog.utils.LambdaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.ArticleMapper;
import com.iris.blog.dao.entity.ArticleEntity;
import com.iris.blog.domain.dto.ArticleDTO;
import com.iris.blog.domain.vo.ArticleVO;
import com.iris.blog.service.ArticleService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章表
 */
@Service("articleService")
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {

    @Resource
    private ArticleTagService articleTagService;
    @Resource
    private ArticleCategoryService articleCategoryService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public R selectArticleList(PageReq<SearchArticleDTO> req){

        Page<ArticleVO> page = new Page<>(req.getPageNo(), req.getPageSize());
        page = this.baseMapper.selectArticleList(page,req.getAction());
        if(CollectionUtils.isNotEmpty(page.getRecords())){
            page.getRecords().stream().map(item->{
                if(StringUtils.isNotBlank(item.getTags())){
                    item.setTagNameList(LambdaUtil.StringTolistComma(item.getTags()));
                    return item;
                }else {
                    item.setTagNameList(Collections.EMPTY_LIST);
                }
                return item;
            }).collect(Collectors.toList());
        }
        PageBean<ArticleVO> pageBean = PageUtil.pageBean(page, ArticleVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectArticleById( Long id){

        ArticleEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        ArticleVO vo = BeanUtil.newBean(entity, ArticleVO.class);
        if(StringUtils.isNotBlank(entity.getTags())){
            vo.setTagNameList(articleTagService.getTagsByIds(LambdaUtil.StringTolistComma(entity.getTags())));
            vo.setImageDetails(StringUtils.isBlank(entity.getImageDetails()) ? null : LambdaUtil.StringTolistComma(entity.getImageDetails()));
        }
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveArticle(ArticleDTO dto){
        SessionUserVO user = (SessionUserVO) StpUtil.getSession().get(CommonConstant.LOGIN_USER);
        ArticleEntity entity = BeanUtil.newBean(dto, ArticleEntity.class);
        if(CollectionUtils.isNotEmpty(dto.getImageDetails())){
            entity.setImageDetails(LambdaUtil.listToStringComma(dto.getImageDetails(), String::valueOf));
        }
        //添加分类
        Long categoryId = articleCategoryService.savaCategoryByName(dto.getCategoryName());
        //添加标签
        String tags = articleTagService.savaTagByName(dto.getTagNameList());
        entity.setCategoryId(categoryId);
        entity.setTags(tags);
        entity.setAuthorId(user.getId());
        entity.setAuthorName(user.getUsername());
        this.save(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateArticle(ArticleDTO dto){
        ArticleEntity articleEntity = this.checkExist(dto.getId());
        //只能修改属于当前登录用户的文章
        String loginId = StpUtil.getLoginIdAsString();
        if (!articleEntity.getAuthorId().equals(loginId) && !StpUtil.hasRole("super_admin")){
            throw new BusinessException(ResultCode.NO_PERMISSION);
        }
        ArticleEntity entity = BeanUtil.newBean(dto, ArticleEntity.class);
        if(CollectionUtils.isNotEmpty(dto.getImageDetails())){
            entity.setImageDetails(LambdaUtil.listToStringComma(dto.getImageDetails(), String::valueOf));
        }
        //添加分类
        Long categoryId = articleCategoryService.savaCategoryByName(dto.getCategoryName());
        //添加标签
        String tags = articleTagService.savaTagByName(dto.getTagNameList());
        entity.setCategoryId(categoryId);
        entity.setTags(tags);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeArticleByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    public R topArticle(Long id) {
        ArticleEntity articleEntity = checkExist(id);
        articleEntity.setIsTop(!articleEntity.getIsTop());
        this.updateById(articleEntity);
        return R.ok();
    }

    @Override
    public R psArticle(Long id,Integer status) {
        ArticleEntity articleEntity = checkExist(id);
        articleEntity.setStatus(status);
        this.updateById(articleEntity);
        return R.ok();
    }

    @Override
    public List<SystemHomeContributeStatisticsVO> selectArticleStatistics() {

        return this.baseMapper.selectArticleStatistics();
    }

    @Override
    public Long getCount() {

        return this.count(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getStatus, BaseNumberEnum.ONE.getCode()));
    }

    @Override
    public R<List<AppArticleRecommendVO>> articleRecommendList() {

        LambdaQueryWrapper<ArticleEntity> queryWrapper = Wrappers.<ArticleEntity>lambdaQuery()
                .eq(ArticleEntity::getStatus, BaseNumberEnum.ONE.getCode())
                .eq(ArticleEntity::getIsRecommend,Boolean.TRUE)
                .last("limit 5");
        List<ArticleEntity> list = this.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            List<AppArticleRecommendVO> voList = list.stream().map(item-> {
                AppArticleRecommendVO vo = BeanUtil.newBean(item, AppArticleRecommendVO.class);
                vo.setArticleTitle(item.getTitle());
                vo.setArticleCover(item.getCoverImage());
                return vo;
            }).collect(Collectors.toList());
            return R.ok(voList);
        }
        return R.ok(Collections.EMPTY_LIST);
    }

    @Override
    public R<PageBean<AppArticleHomeVO>> articleHomeList(PageReq<AppSearchAppArticleDTO>  req) {
        Page<AppArticleHomeVO> page = new Page<>(req.getPageNo(), req.getPageSize());
        page = this.baseMapper.articleHomeList(page, req.getAction());
        if(CollectionUtils.isNotEmpty(page.getRecords())){
            page.getRecords().stream().map(item->{
                if(StringUtils.isNotBlank(item.getImageDetailsStr())){
                    item.setImageDetails(LambdaUtil.StringTolistComma(item.getImageDetailsStr()));
                    return item;
                }else {
                    item.setImageDetails(Collections.EMPTY_LIST);
                }
                return item;
            }).collect(Collectors.toList());
        }
        PageBean<AppArticleHomeVO> pageBean = PageUtil.pageBean(page, AppArticleHomeVO.class);
        return R.ok(pageBean);
    }

    @Override
    public R<PageBean<AppArticleArchiveVO>> articleArchivesList(PageReq<?> req) {
        Page<AppArticleArchiveVO> page = new Page<>(req.getPageNo(), req.getPageSize());
        page = this.baseMapper.articleArchivesList(page);
        PageBean<AppArticleArchiveVO> pageBean = PageUtil.pageBean(page, AppArticleArchiveVO.class);
        return R.ok(pageBean);
    }

    @Override
    public R<List<AppArticleSearchVO>> articleSearchList(AppSearchAppArticleDTO dto) {

        List<AppArticleSearchVO> list = this.baseMapper.articleSearchList(dto);

        list = list.stream()
                .map(article -> {
                    // 获取关键词第一次出现的位置
                    String articleContent = article.getArticleContent();
                    int index = article.getArticleContent().indexOf(dto.getKeyword());
                    if (index != -1) {
                        // 获取关键词前面的文字
                        int preIndex = index > 25 ? index - 25 : 0;
                        String preText = article.getArticleContent().substring(preIndex, index);
                        // 获取关键词到后面的文字
                        int last = index + dto.getKeyword().length();
                        int postLength = article.getArticleContent().length() - last;
                        int postIndex = postLength > 175 ? last + 175 : last + postLength;
                        String postText = article.getArticleContent().substring(index, postIndex);
                        // 文章内容高亮
                        articleContent = (preText + postText).replaceAll(dto.getKeyword(), CommonConstant.PRE_TAG + dto.getKeyword() + CommonConstant.POST_TAG);
                    }
                    // 文章标题高亮
                    String articleTitle = article.getArticleTitle().replaceAll(dto.getKeyword(), CommonConstant.PRE_TAG + dto.getKeyword() + CommonConstant.POST_TAG);
                    return AppArticleSearchVO.builder()
                            .id(article.getId())
                            .articleTitle(articleTitle)
                            .articleContent(articleContent)
                            .build();
                })
                .collect(Collectors.toList());

        return R.ok(list);
    }

    @Override
    public R<AppArticleDetailVO> getArticleHomeById(Integer articleId) {
        // 查询文章信息
        AppArticleDetailVO article = this.baseMapper.selectArticleHomeById(articleId);
        if (Objects.isNull(article)) {
            return null;
        }
        // 浏览量+1
        redisUtil.incrementHashKey(RedisKeyConstant.ARTICLE_VIEW_COUNT, articleId.toString(), 1L);
        // 查询上一篇文章
        AppArticlePaginationVO lastArticle = this.baseMapper.selectLastArticle(articleId);
        // 查询下一篇文章
        AppArticlePaginationVO nextArticle = this.baseMapper.selectNextArticle(articleId);
        article.setLastArticle(lastArticle);
        article.setNextArticle(nextArticle);
        // 查询浏览量
        Integer viewCount = (Integer) redisUtil.getHashKey(RedisKeyConstant.ARTICLE_VIEW_COUNT, articleId.toString());
        article.setViewCount(viewCount);
        // 查询点赞量
        Integer likeCount = (Integer) redisUtil.getHashKey(RedisKeyConstant.ARTICLE_LIKE_COUNT, articleId.toString());
        article.setLikeCount(Optional.ofNullable(likeCount).orElse(0));
        return R.ok(article);
    }

    @Override
    public void updateArticleStats(List<ArticleEntity> list) {
        this.baseMapper.updateArticleStats(list);
    }


    public ArticleEntity checkExist(Long id){
        ArticleEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}