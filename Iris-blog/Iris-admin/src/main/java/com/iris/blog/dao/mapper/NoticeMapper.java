package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.NoticeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.blog.domain.vo.app.AppNoticeCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lstar
 * @date: 2024-09
 * @description: 系统通知
 */
@Mapper
public interface NoticeMapper extends BaseMapper<NoticeEntity> {

    /**
     * 获取未读消息数
     * @param userId
     * @return
     */
    AppNoticeCountVO readAllNotice(@Param("userId") long userId);
}
