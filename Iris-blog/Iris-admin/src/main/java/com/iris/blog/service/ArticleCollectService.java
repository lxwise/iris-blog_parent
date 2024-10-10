package com.iris.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.ArticleCollectEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.ArticleCollectDTO;
/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章收藏表
 */
public interface ArticleCollectService extends IService<ArticleCollectEntity> {

    /**
    * 我的收藏列表
     * @param req
     * @return
    */
    R selectArticleCollectList(PageReq req);

    /**
     * 收藏文章
     * @param id
     * @return
     */
    R collect(Long id);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    R cancelCollect(Long id);
}

