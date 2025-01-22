package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.common.PageBean;
import com.iris.blog.dao.entity.ArticleEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.ArticleDTO;
import com.iris.blog.domain.search.SearchArticleDTO;
import com.iris.blog.domain.search.app.AppSearchAppArticleDTO;
import com.iris.blog.domain.vo.SystemHomeContributeStatisticsVO;
import com.iris.blog.domain.vo.app.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章表
 */
public interface ArticleService extends IService<ArticleEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectArticleList(PageReq<SearchArticleDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectArticleById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveArticle(ArticleDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateArticle(ArticleDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeArticleByIds(List<Long> ids);

    /**
     * 置顶文章
     * @param id
     * @return
     */
    R topArticle(Long id);

    /**
     * 发布或下架文章
     * @param id
     * @return
     */
    R psArticle(Long id,Integer status);

    /**
     *文章贡献统计
     * @return
     */
    List<SystemHomeContributeStatisticsVO> selectArticleStatistics();

    /**
     * 文章数
     * @return
     */
    Long getCount();

    /**
     *推荐文章 列表
     * @return
     */
    R<List<AppArticleRecommendVO>> articleRecommendList();

    /**
     * 文章首页列表
     * @param req
     * @return
     */
    R<PageBean<AppArticleHomeVO>> articleHomeList(PageReq<AppSearchAppArticleDTO>  req);

    /**
     * 文章归档列表
     * @param req
     * @return
     */
    R<PageBean<AppArticleArchiveVO>> articleArchivesList(PageReq<?>  req);

    /**
     * 文章搜索列表
     * @param dto
     * @return
     */
    R<List<AppArticleSearchVO>> articleSearchList(AppSearchAppArticleDTO dto);

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    R<AppArticleDetailVO> getArticleHomeById(Integer articleId);

    /**
     * 更新文章统计
     * @param list
     */
    void updateArticleStats(List<ArticleEntity> list);

    /**
     * 文章导入
     * @param file
     * @return
     */
    R importArticle(MultipartFile file);

    /**
     * 导出文章
     * @param ids
     * @param response
     */
    void exportArticle(List<Long> ids, HttpServletResponse response);
}

