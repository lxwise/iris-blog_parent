package com.iris.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.dao.entity.CommentEntity;
import com.iris.blog.dao.entity.NoticeEntity;
import com.iris.blog.common.R;
import com.iris.blog.domain.dto.NoticeDTO;
import com.iris.blog.domain.vo.NoticeVO;
import com.iris.blog.domain.vo.app.AppNoticeCountVO;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-09
 * @description: 系统通知
 */
public interface NoticeService extends IService<NoticeEntity> {

    /**
     * 查看通知
     * @param pageReq
     * @return
     */
    R<PageBean<NoticeVO>> getNoticeList(PageReq<NoticeDTO> pageReq);

    /**
     * 清空通知
     * @param type
     * @return
     */
    void clearAllNotice(Integer type);

    /**
     * 未读消息数
     * @return
     */
    R<AppNoticeCountVO> readAllNotice();

    /**
     * 发送通知
     * @param userId
     * @param comment
     * @param isComment
     */
    void sendNotice(long userId, CommentEntity comment,Boolean isComment);

    /**
     * 批量发送通知
     * @param userId
     * @param commentList
     */
    void sendBathNotice(long userId, List<CommentEntity> commentList);
}

