package com.iris.blog.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.dao.entity.ArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.blog.domain.search.SearchArticleDTO;
import com.iris.blog.domain.search.app.AppSearchAppArticleDTO;
import com.iris.blog.domain.vo.ArticleVO;
import com.iris.blog.domain.vo.SystemHomeContributeStatisticsVO;
import com.iris.blog.domain.vo.app.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章表
 */
@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {

    Page<ArticleVO> selectArticleList(Page<ArticleVO> page, @Param("action") SearchArticleDTO action);

    List<SystemHomeContributeStatisticsVO> selectArticleStatistics();

    Page<AppArticleHomeVO> articleHomeList(Page<AppArticleHomeVO> page, @Param("action") AppSearchAppArticleDTO action);

    Page<AppArticleArchiveVO> articleArchivesList(Page<AppArticleArchiveVO> page);

    List<AppArticleSearchVO> articleSearchList(@Param("dto") AppSearchAppArticleDTO dto);

    AppArticleDetailVO selectArticleHomeById(@Param("articleId") Integer articleId);

    AppArticlePaginationVO selectLastArticle(@Param("articleId") Integer articleId);

    AppArticlePaginationVO selectNextArticle(@Param("articleId") Integer articleId);

    void updateArticleStats(@Param("list") List<ArticleEntity> list);
}
