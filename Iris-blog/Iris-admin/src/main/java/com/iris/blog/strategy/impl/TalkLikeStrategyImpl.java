package com.iris.blog.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.TalkEntity;
import com.iris.blog.dao.mapper.TalkMapper;
import com.iris.blog.service.CommentService;
import com.iris.blog.service.TalkService;
import com.iris.blog.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 说说点赞策略
 */
@Service("talkLikeStrategyImpl")
public class TalkLikeStrategyImpl implements LikeStrategy {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TalkService talkService;

    @Override
    public void like(Integer talkId) {
        TalkEntity talk = talkService.getById(talkId);
        Assert.isFalse(Objects.isNull(talk) || !Objects.equals(talk.getStatus(), BaseNumberEnum.ONE.getCode()), "说说不存在");
        // 用户id作为键，说说id作为值，记录用户点赞记录
        String userLikeTalkKey = RedisKeyConstant.USER_TALK_LIKE + StpUtil.getLoginIdAsInt();
        // 判断是否点赞
        if (redisUtil.isMemBer(userLikeTalkKey, talkId)) {
            // 取消点赞则删除用户id中的说说id
            redisUtil.delFromSet(userLikeTalkKey, talkId);
            // 说说点赞量-1
            redisUtil.incrementHashKey(RedisKeyConstant.TALK_LIKE_COUNT, talkId.toString(), 1L);
        } else {
            // 点赞则在用户id记录说说id
            redisUtil.addToSet(userLikeTalkKey, talkId);
            // 说说点赞量+1
            redisUtil.incrementHashKey(RedisKeyConstant.TALK_LIKE_COUNT, talkId.toString(), 1L);
        }
    }

}
