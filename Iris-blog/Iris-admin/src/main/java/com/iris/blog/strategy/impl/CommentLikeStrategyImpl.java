package com.iris.blog.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.common.enums.NoticeTypeEnum;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.ArticleEntity;
import com.iris.blog.dao.entity.CommentEntity;
import com.iris.blog.dao.entity.NoticeEntity;
import com.iris.blog.dao.entity.TalkEntity;
import com.iris.blog.dao.mapper.ArticleCommentMapper;
import com.iris.blog.dao.mapper.TalkMapper;
import com.iris.blog.domain.vo.UserVO;
import com.iris.blog.service.ArticleService;
import com.iris.blog.service.CommentService;
import com.iris.blog.service.NoticeService;
import com.iris.blog.service.UserService;
import com.iris.blog.strategy.LikeStrategy;
import com.iris.blog.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 评论点赞策略
 */
@Service("commentLikeStrategyImpl")
public class CommentLikeStrategyImpl implements LikeStrategy {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CommentService commentService;
    @Resource
    private NoticeService noticeService;
    @Override
    public void like(Integer commentId) {
        // 判断评论是否存在或是否通过或是否进入回收站
        CommentEntity comment = commentService.getById(commentId);
        Assert.isFalse(Objects.isNull(comment) || !Objects.equals(comment.getStatus(), BaseNumberEnum.ONE.getCode()), "评论不存在");
        // 用户id作为键，评论id作为值，记录用户点赞记录
        String userLikeCommentKey = RedisKeyConstant.USER_COMMENT_LIKE + StpUtil.getLoginIdAsInt();
        if (redisUtil.isMemBer(userLikeCommentKey, commentId)) {
            // 取消点赞则删除用户id中的评论id
            redisUtil.delFromSet(userLikeCommentKey, commentId);
            // 评论点赞量-1
            redisUtil.incrementHashKey(RedisKeyConstant.COMMENT_LIKE_COUNT, commentId.toString(), -1L);
        } else {
            // 点赞则在用户id记录评论id
            redisUtil.addToSet(userLikeCommentKey, commentId);
            // 评论点赞量+1
            redisUtil.incrementHashKey(RedisKeyConstant.COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }

        // 发送通知
        long userId = StpUtil.getLoginIdAsLong();
        this.noticeService.sendNotice(userId, comment, false);
    }

}