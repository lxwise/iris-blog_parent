package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.FeedBackEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.FeedBackDTO;
import com.iris.blog.domain.dto.app.AppFeedBackDTO;
import com.iris.blog.domain.search.SearchFeedBackDTO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户反馈表
 */
public interface FeedBackService extends IService<FeedBackEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectFeedBackList(PageReq<SearchFeedBackDTO> req);
    /**
     * 删除
     * @param ids
     * @return
     */
    R removeFeedBackByIds(List<Long> ids);

    /**
     * 修改用户反馈状态
     * @param id
     * @return
     */
    R updateStatus(Long id);

    /**
     * 添加用户反馈
     * @param dto
     * @return
     */
    R saveFeedback(AppFeedBackDTO dto);
}

