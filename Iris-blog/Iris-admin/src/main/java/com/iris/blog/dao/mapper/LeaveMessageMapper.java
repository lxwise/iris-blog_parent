package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.LeaveMessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户留言表
 */
@Mapper
public interface LeaveMessageMapper extends BaseMapper<LeaveMessageEntity> {
	
}
