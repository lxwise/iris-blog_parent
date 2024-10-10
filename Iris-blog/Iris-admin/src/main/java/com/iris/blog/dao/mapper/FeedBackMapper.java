package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.FeedBackEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户反馈表
 */
@Mapper
public interface FeedBackMapper extends BaseMapper<FeedBackEntity> {
	
}
