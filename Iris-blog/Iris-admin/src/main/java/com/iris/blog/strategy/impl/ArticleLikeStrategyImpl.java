package com.iris.blog.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;

import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.ArticleEntity;
import com.iris.blog.service.ArticleService;
import com.iris.blog.strategy.LikeStrategy;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章点赞策略
 */
@Service("articleLikeStrategyImpl")
public class ArticleLikeStrategyImpl implements LikeStrategy {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ArticleService articleService;

    @Override
    public void like(Integer articleId) {
        // 判断文章是否发布
        ArticleEntity article = articleService.getById(articleId);
        Assert.isFalse(Objects.isNull(article) || !Objects.equals(article.getStatus(), BaseNumberEnum.ONE.getCode()), "文章不存在");
        // 用户id作为键，文章id作为值，记录用户点赞记录
        String userLikeArticleKey = RedisKeyConstant.USER_ARTICLE_LIKE + StpUtil.getLoginIdAsInt();
        // 判断是否点赞
        if (redisUtil.isMemBer(userLikeArticleKey, articleId)) {
            // 取消点赞则删除用户id中的文章id
            redisUtil.delFromSet(userLikeArticleKey, articleId);
            // 文章点赞量-1
            redisUtil.incrementHashKey(RedisKeyConstant.ARTICLE_LIKE_COUNT, articleId.toString(), -1L);
        } else {
            // 点赞则在用户id记录文章id
            redisUtil.addToSet(userLikeArticleKey, articleId);
            // 文章点赞量+1
            redisUtil.incrementHashKey(RedisKeyConstant.ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }
}