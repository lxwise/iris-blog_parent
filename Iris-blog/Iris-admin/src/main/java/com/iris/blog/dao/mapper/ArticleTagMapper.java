package com.iris.blog.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.dao.entity.ArticleTagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.vo.ArticleTagVO;
import com.iris.blog.domain.vo.app.AppArticleTagVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章标签表
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTagEntity> {

    Page<ArticleTagVO> selectArticleTagList(Page<ArticleTagVO> page, @Param("action") SearchNameDTO action);

    int validateTagHasExistArticle(@Param("id") Long id);

    List<AppArticleTagVO> articleTagList();
}
