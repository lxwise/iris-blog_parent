package com.iris.blog.strategy;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 点赞策略
 */
public interface LikeStrategy {

    /**
     * 点赞
     *
     * @param typeId 类型id
     */
    void like(Integer typeId);
}
