package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.OperateLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统操作日志
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLogEntity> {
	
}
