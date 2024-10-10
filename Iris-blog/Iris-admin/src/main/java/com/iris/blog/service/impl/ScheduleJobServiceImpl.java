package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.constant.CommonConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.domain.search.SearchScheduleJobDTO;
import com.iris.blog.domain.vo.SessionUserVO;
import com.iris.blog.components.quartz.utils.ScheduleJobUtil;
import com.iris.blog.components.quartz.utils.ScheduleUtils;
import com.iris.blog.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.ScheduleJobMapper;
import com.iris.blog.dao.entity.ScheduleJobEntity;
import com.iris.blog.domain.dto.ScheduleJobDTO;
import com.iris.blog.domain.vo.ScheduleJobVO;
import com.iris.blog.service.ScheduleJobService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import com.iris.blog.components.quartz.utils.ScheduleStatusEnum;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度表
 */
@Service("scheduleJobService")
@Slf4j
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJobEntity> implements ScheduleJobService {

    @Resource
    private Scheduler scheduler;
    @Override
    public R selectScheduleJobList(PageReq<SearchScheduleJobDTO> req){

        ScheduleJobEntity entity = BeanUtil.newBean(req.getAction(), ScheduleJobEntity.class);
        Page<ScheduleJobEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(ScheduleJobEntity::getId));

        PageBean<ScheduleJobVO> pageBean = PageUtil.pageBean(page, ScheduleJobVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectScheduleJobById( Long id){

        ScheduleJobEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        ScheduleJobVO vo = BeanUtil.newBean(entity, ScheduleJobVO.class);
        List<LocalDateTime> nextTimes = ScheduleJobUtil.getNextTimes(entity.getCronExpression(), BaseNumberEnum.FIVE.getCode());
        vo.setNextValidTime(nextTimes);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveScheduleJob(ScheduleJobDTO dto){
        // 校验cron表达式
        checkCronIsValid(dto);

        // 当前操作用户
        SessionUserVO user = (SessionUserVO) StpUtil.getSession().get(CommonConstant.LOGIN_USER);
        ScheduleJobEntity entity = BeanUtil.newBean(dto, ScheduleJobEntity.class);
        entity.setCreateBy(user.getUsername());
        this.save(entity);

        //创建定时任务
        ScheduleUtils.createScheduleJob(scheduler, entity);

        return R.ok();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateScheduleJob(ScheduleJobDTO dto){

        // 校验cron表达式
        checkCronIsValid(dto);
        ScheduleJobEntity jobEntity = this.checkExist(dto.getId());

        // 当前操作用户
        SessionUserVO user = (SessionUserVO) StpUtil.getSession().get(CommonConstant.LOGIN_USER);
        BeanUtil.copyProperties(dto, jobEntity);
        jobEntity.setUpdateBy(user.getUsername());
        this.updateById(jobEntity);

        //更新任务
        ScheduleUtils.updateScheduleJob(scheduler, jobEntity);
        return R.ok();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeScheduleJobByIds(Long id){
        checkExist(id);
        this.removeById(id);
        ScheduleUtils.deleteScheduleJob(scheduler, id);
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R run(Long id) {
        ScheduleJobEntity job = checkExist(id);
        if(job.getStatus() == BaseNumberEnum.ZERO.getCode()){
            throw new BusinessException("任务已关闭,请开启后执行");
        }
        ScheduleUtils.run(scheduler, job);
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R changeStatus(Long id) {
        ScheduleJobEntity job = checkExist(id);
        job.setStatus(job.getStatus() == ScheduleStatusEnum.PAUSE.value() ? ScheduleStatusEnum.NORMAL.value() : ScheduleStatusEnum.PAUSE.value());
        this.updateById(job);
        if (ScheduleStatusEnum.NORMAL.value() == job.getStatus()) {
            ScheduleUtils.resumeJob(scheduler, id);
        }
        if (ScheduleStatusEnum.PAUSE.value() == job.getStatus()) {
            ScheduleUtils.pauseJob(scheduler, id);
        }
        return R.ok();
    }


    /**
     * 校验cron表达式
     * @param dto
     */
    private void checkCronIsValid(ScheduleJobDTO dto) {
        boolean valid = ScheduleJobUtil.isValid(dto.getCronExpression());
        Assert.isTrue(valid,"Cron表达式无效!");
    }

    public ScheduleJobEntity checkExist(Long id){
        ScheduleJobEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}