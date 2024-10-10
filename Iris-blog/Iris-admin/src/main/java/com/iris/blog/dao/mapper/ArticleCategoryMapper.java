package com.iris.blog.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.dao.entity.ArticleCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.search.app.AppSearchAppArticleDTO;
import com.iris.blog.domain.vo.ArticleCategoryVO;
import com.iris.blog.domain.vo.app.AppArticleHomeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章分类表
 */
@Mapper
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategoryEntity> {

    Page<ArticleCategoryVO> selectArticleCategoryList(Page<ArticleCategoryVO> page, @Param("action") SearchNameDTO action);

    int validateCategoryHasExistArticle(@Param("id") Long id);

    List<ArticleCategoryVO> articleCategoryCount();

    Page<AppArticleHomeVO> articleCategoryList(Page<AppArticleHomeVO> page, @Param("action") AppSearchAppArticleDTO action);
}
