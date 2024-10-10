package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.common.PageBean;
import com.iris.blog.dao.entity.TalkEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.TalkDTO;
import com.iris.blog.domain.vo.TalkVO;

import javax.servlet.http.HttpServletResponse;
/**
 * @author lstar
 * @date: 2024-08
 * @description: 说说
 */
public interface TalkService extends IService<TalkEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectTalkList(PageReq req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectTalkById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveTalk(TalkDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateTalk(TalkDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeTalkByIds(List<Long> ids);

    /**
     * 查看首页说说
     * @return
     */
    R<List<String>> listTalkHome();

    /**
     * 说说列表
     * @param req
     * @return
     */
    R<PageBean<TalkVO>> talkList(PageReq req);

    /**
     * 根据说说id查询说说
     * @param talkId
     * @return
     */
    R<TalkVO> getTalkById(Integer talkId);
}

