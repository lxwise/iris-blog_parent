package com.iris.blog.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.common.PageBean;
import com.iris.blog.dao.entity.ArticleCategoryEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.ArticleCategoryDTO;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.search.app.AppSearchAppArticleDTO;
import com.iris.blog.domain.vo.ArticleCategoryVO;
import com.iris.blog.domain.vo.app.AppArticleCategoryVO;
import com.iris.blog.domain.vo.app.AppArticleHomeVO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章分类表
 */
public interface ArticleCategoryService extends IService<ArticleCategoryEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectArticleCategoryList(PageReq<SearchNameDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectArticleCategoryById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveArticleCategory(ArticleCategoryDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateArticleCategory(ArticleCategoryDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeArticleCategoryByIds(List<Long> ids);

    /**
     * 下拉
     * @param name
     * @return
     */
    R<List<ArticleCategoryVO>> selectList(String name);

    /**
     * 添加分类名
     * @param categoryName
     * @return
     */
    Long savaCategoryByName(String categoryName);

    /**
     * 分类统计
     * @return
     */
    List<ArticleCategoryVO>  articleCategoryCount();

    /**
     * 分类列表
     * @return
     */
    R<List<AppArticleCategoryVO>> listCategory();

    /**
     * 分类文章列表
     * @param req
     * @return
     */
    R<PageBean<AppArticleHomeVO>> articleCategoryList(PageReq<AppSearchAppArticleDTO> req);
}

