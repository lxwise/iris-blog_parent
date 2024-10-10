package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.LeaveMessageEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.LeaveMessageDTO;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.dto.app.AppLeaveMessageDTO;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.vo.LeaveMessageVO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户留言表
 */
public interface LeaveMessageService extends IService<LeaveMessageEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectLeaveMessageList(PageReq<SearchNameDTO> req);


    /**
     * 删除
     * @param ids
     * @return
     */
    R removeLeaveMessageByIds(List<Long> ids);

    /**
     * 修改用户留言状态
     * @param dto
     * @return
     */
    R updateStatus(UpdateCommentStatusDTO dto);

    /**
     * 查看留言列表
     * @return
     */
    R<List<LeaveMessageVO>> selectMessageList();

    /**
     * 添加留言
     * @param dto
     */
    void saveMessage(AppLeaveMessageDTO dto);
}

