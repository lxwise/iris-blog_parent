package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.ScheduleJobLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度日志表
 */
@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLogEntity> {
	
}
