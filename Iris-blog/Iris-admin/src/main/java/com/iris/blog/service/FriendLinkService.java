package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.FriendLinkEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.FriendLinkDTO;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.search.SearchFriendLinkDTO;
import com.iris.blog.domain.vo.FriendLinkVO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 友情链接表
 */
public interface FriendLinkService extends IService<FriendLinkEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectFriendLinkList(PageReq<SearchFriendLinkDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectFriendLinkById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveFriendLink(FriendLinkDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateFriendLink(FriendLinkDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeFriendLinkByIds(List<Long> ids);

    /**
     * 置顶
     * @param id
     * @return
     */
    R topFriendLink(Long id);

    /**
     * 修改友链状态
     * @param id
     * @param status
     * @return
     */
    R updateStatus(Long id,Integer status);

    /**
     * 友链列表
     * @return
     */
    R<List<FriendLinkVO>> friendLinkList();
}

