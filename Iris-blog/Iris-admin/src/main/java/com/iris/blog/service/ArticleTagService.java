package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.ArticleTagEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.ArticleTagDTO;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.vo.ArticleTagVO;
import com.iris.blog.domain.vo.app.AppArticleTagVO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章标签表
 */
public interface ArticleTagService extends IService<ArticleTagEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectArticleTagList(PageReq<SearchNameDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectArticleTagById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveArticleTag(ArticleTagDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateArticleTag(ArticleTagDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeArticleTagByIds(List<Long> ids);

    /**
     * 根据id获取标签
     * @param strings
     * @return
     */
    List<String> getTagsByIds(List<String> strings);

    /**
     * 下拉
     * @param name
     * @return
     */
    List<ArticleTagVO> selectList(String name);

    /**
     * 将不存在的标签新增
     * @param tagNameList
     * @return
     */
    String savaTagByName(List<String> tagNameList);

    /**
     * 文章标签列表
     * @return
     */
    R<List<AppArticleTagVO>> articleTagList();
}

