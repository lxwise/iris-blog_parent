package com.iris.blog.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.dao.entity.TalkEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.blog.domain.vo.TalkVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-08
 * @description: 说说
 */
@Mapper
public interface TalkMapper extends BaseMapper<TalkEntity> {

    TalkVO selectTalkById(@Param("talkId") Integer talkId);

    List<TalkVO> talkList(Page<TalkVO> page);
}
