package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.domain.search.SearchScheduleJobLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.ScheduleJobLogMapper;
import com.iris.blog.dao.entity.ScheduleJobLogEntity;
import com.iris.blog.domain.vo.ScheduleJobLogVO;
import com.iris.blog.service.ScheduleJobLogService;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度日志表
 */
@Service("scheduleJobLogService")
@Slf4j
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLogEntity> implements ScheduleJobLogService {

    @Override
    public R selectScheduleJobLogList(PageReq<SearchScheduleJobLogDTO> req){

        SearchScheduleJobLogDTO action = req.getAction();
        ScheduleJobLogEntity entity = BeanUtil.newBean(req.getAction(), ScheduleJobLogEntity.class);
        Page<ScheduleJobLogEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .gt(action.getStartDateTime() != null,ScheduleJobLogEntity::getStartTime,action.getStartDateTime())
                        .lt(action.getStopDateTime() != null,ScheduleJobLogEntity::getStartTime,action.getStopDateTime())
                        .orderByDesc(ScheduleJobLogEntity::getId));

        PageBean<ScheduleJobLogVO> pageBean = PageUtil.pageBean(page, ScheduleJobLogVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectScheduleJobLogById( Long id){

        ScheduleJobLogEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        ScheduleJobLogVO vo = BeanUtil.newBean(entity, ScheduleJobLogVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeScheduleJobLogByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    public ScheduleJobLogEntity checkExist(Long id){
        ScheduleJobLogEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}