package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.common.PageBean;
import com.iris.blog.dao.entity.CommentEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.dto.app.AppCommentDTO;
import com.iris.blog.domain.search.SearchCommentDTO;
import com.iris.blog.domain.search.app.AppSearchCommentDTO;
import com.iris.blog.domain.vo.app.AppCommentCountVO;
import com.iris.blog.domain.vo.app.AppCommentVO;
import com.iris.blog.domain.vo.app.AppRecentCommentLatestVO;
import com.iris.blog.domain.vo.app.AppReplyCommentVO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章评论表
 */
public interface CommentService extends IService<CommentEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectCommentList(PageReq<SearchCommentDTO> req);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeCommentByIds(List<Long> ids);

    /**
     * 审核
     * @param dto
     * @return
     */
    R updateStatus(UpdateCommentStatusDTO dto);

    /**
     * 最新评论列表
     * @return
     */
    R<List<AppRecentCommentLatestVO>> latestRecentCommentList();

    /**
     * 添加评论
     * @param dto
     */
    void addComment(AppCommentDTO dto);

    /**
     * 查看评论
     * @param req
     * @return
     */
    R<PageBean<AppCommentVO>> viewCommentList(PageReq<AppSearchCommentDTO> req);

    /**
     * 查看回复评论
     * @param req
     * @return
     */
    R<PageBean<AppReplyCommentVO>> viewReplyCommentList(PageReq<AppSearchCommentDTO> req);

    /**
     * 根据类型id查询评论数量
     * @param talkIdList
     * @param type
     * @return
     */
    List<AppCommentCountVO> selectCommentCountByTypeId(List<Long> talkIdList, Integer type);
}

