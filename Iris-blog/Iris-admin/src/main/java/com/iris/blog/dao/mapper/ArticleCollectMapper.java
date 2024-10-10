package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.ArticleCollectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章收藏表
 */
@Mapper
public interface ArticleCollectMapper extends BaseMapper<ArticleCollectEntity> {
	
}
