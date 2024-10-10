package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import com.iris.blog.dao.mapper.OperateLogMapper;
import com.iris.blog.dao.entity.OperateLogEntity;
import com.iris.blog.domain.dto.OperateLogDTO;
import com.iris.blog.domain.vo.OperateLogVO;
import com.iris.blog.service.OperateLogService;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统操作日志
 */
@Service("operateLogService")
@Slf4j
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLogEntity> implements OperateLogService {

    @Override
    public R selectOperateLogList(PageReq<OperateLogDTO> req){

        OperateLogEntity entity = BeanUtil.newBean(req.getAction(), OperateLogEntity.class);
        Page<OperateLogEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .ge(null != req.getAction().getCreateTimeStart(),OperateLogEntity::getCreateTime,req.getAction().getCreateTimeStart())
                        .le(null != req.getAction().getCreateTimeEnd(),OperateLogEntity::getCreateTime,req.getAction().getCreateTimeEnd())
                        .orderByDesc(OperateLogEntity::getId));

        PageBean<OperateLogVO> pageBean = PageUtil.pageBean(page, OperateLogVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectOperateLogById( Long id){

        OperateLogEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        OperateLogVO vo = BeanUtil.newBean(entity, OperateLogVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeOperateLogByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }
}