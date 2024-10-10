package com.iris.blog.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.common.R;
import com.iris.blog.dao.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.blog.domain.search.SearchCommentDTO;
import com.iris.blog.domain.search.app.AppSearchCommentDTO;
import com.iris.blog.domain.vo.CommentVO;
import com.iris.blog.domain.vo.app.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章评论表
 */
@Mapper
public interface ArticleCommentMapper extends BaseMapper<CommentEntity> {

    Page<CommentVO> selectCommentList(Page<CommentVO> page, @Param("action") SearchCommentDTO action);

    List<AppRecentCommentLatestVO> latestRecentCommentList();

    /**
     * 根据父评论id查询子评论id
     *
     * @param parentId 父评论id
     * @return 子评论id列表
     */
    List<Long> selectCommentIdByParentId(@Param("parentId") Long parentId);

    /**
     *分页查询父评论
     * @param page
     * @param action
     * @return
     */
    Page<AppCommentVO> selectParentComment(Page<AppCommentVO> page, @Param("action") AppSearchCommentDTO action);

    /**
     * 查询每条父评论下的前三条子评论
     *
     * @param parentCommentIdList
     * @return
     */
    List<AppReplyCommentVO> selectReplyByParentIdListLimit3(@Param("parentCommentIdList") List<Integer> parentCommentIdList);

    /**
     * 根据父评论id查询回复数量
     *
     * @param parentCommentIdList
     * @return
     */
    List<AppReplyCommentCountVO> selectReplyCountByParentId(@Param("parentCommentIdList") List<Integer> parentCommentIdList);

    /**
     * 查询父评论下的子评论
     *
     * @param page
     * @param commentId
     * @return
     */
    Page<AppReplyCommentVO> selectReplyByParentId(Page<AppReplyCommentVO> page, @Param("commentId") Long commentId);

    /**
     * 查询评论数量
     *
     * @param talkIdList
     * @param type
     * @return
     */
    List<AppCommentCountVO> selectCommentCountByTypeId(@Param("talkIdList") List<Long> talkIdList, @Param("type") Integer type);
}
