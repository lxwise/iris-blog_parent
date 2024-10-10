package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.ScheduleJobEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.ScheduleJobDTO;
import com.iris.blog.domain.search.SearchScheduleJobDTO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度表
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectScheduleJobList(PageReq<SearchScheduleJobDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectScheduleJobById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveScheduleJob(ScheduleJobDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateScheduleJob(ScheduleJobDTO dto);

    /**
     * 删除
     * @param id
     * @return
     */
    R removeScheduleJobByIds(Long id);

    /**
     * 立即执行
     * @param id
     * @return
     */
    R run(Long id);

    /**
     * 暂停或恢复
     * @return
     */
    R changeStatus(Long id);
}

