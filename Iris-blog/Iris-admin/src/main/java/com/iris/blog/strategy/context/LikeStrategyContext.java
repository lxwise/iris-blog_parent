package com.iris.blog.strategy.context;


import com.iris.blog.common.enums.LikeTypeEnum;
import com.iris.blog.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 点赞策略上下文
 */
@Service
public class LikeStrategyContext {

    @Autowired
    private Map<String, LikeStrategy> likeStrategyMap;

    /**
     * 点赞
     *
     * @param likeType 点赞类型
     * @param typeId   类型id
     */
    public void executeLikeStrategy(LikeTypeEnum likeType, Integer typeId) {
        likeStrategyMap.get(likeType.getStrategy()).like(typeId);
    }
}