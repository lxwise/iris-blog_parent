package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.ScheduleJobLogEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.ScheduleJobLogDTO;
import com.iris.blog.domain.search.SearchScheduleJobLogDTO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度日志表
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectScheduleJobLogList(PageReq<SearchScheduleJobLogDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectScheduleJobLogById( Long id);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeScheduleJobLogByIds(List<Long> ids);

}

