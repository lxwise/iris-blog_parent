package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.dto.app.AppLeaveMessageDTO;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.domain.vo.SiteConfigVO;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.LeaveMessageMapper;
import com.iris.blog.dao.entity.LeaveMessageEntity;
import com.iris.blog.domain.vo.LeaveMessageVO;
import com.iris.blog.service.LeaveMessageService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户留言表
 */
@Service("leaveMessageService")
@Slf4j
public class LeaveMessageServiceImpl extends ServiceImpl<LeaveMessageMapper, LeaveMessageEntity> implements LeaveMessageService {

    @Override
    public R selectLeaveMessageList(PageReq<SearchNameDTO> req){

        Page<LeaveMessageEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.<LeaveMessageEntity>lambdaQuery()
                        .eq(null != req.getAction().getStatus(),LeaveMessageEntity::getStatus, req.getAction().getStatus())
                        .like(StringUtils.isNotBlank(req.getAction().getName()),LeaveMessageEntity::getNickname, req.getAction().getName())
                        .ge(null != req.getAction().getCreateTimeStart(), LeaveMessageEntity::getCreateTime,req.getAction().getCreateTimeStart())
                        .le(null != req.getAction().getCreateTimeEnd(),LeaveMessageEntity::getCreateTime,req.getAction().getCreateTimeEnd())
                        .orderByDesc(LeaveMessageEntity::getId));

        PageBean<LeaveMessageVO> pageBean = PageUtil.pageBean(page, LeaveMessageVO.class);
        return R.ok(pageBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeLeaveMessageByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateStatus(UpdateCommentStatusDTO dto) {
        LambdaUpdateWrapper<LeaveMessageEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.in(LeaveMessageEntity::getId,dto.getIds())
                .set(LeaveMessageEntity::getStatus,dto.getStatus());
        this.update(updateWrapper);
        return R.ok();
    }

    @Override
    public R<List<LeaveMessageVO>> selectMessageList() {
        // 查询留言列表
        List<LeaveMessageEntity> messageList = this.list(new LambdaQueryWrapper<LeaveMessageEntity>()
                        .eq(LeaveMessageEntity::getStatus, BaseNumberEnum.ONE.getCode())
                .select(LeaveMessageEntity::getId, LeaveMessageEntity::getNickname, LeaveMessageEntity::getAvatar,
                        LeaveMessageEntity::getContent, LeaveMessageEntity::getCreateTime));

        if(CollectionUtils.isEmpty(messageList)){
            return R.ok(Collections.EMPTY_LIST);
        }
        List<LeaveMessageVO> list = messageList.stream().map(item -> BeanUtil.newBean(item, LeaveMessageVO.class)).toList();
        return R.ok(list);
    }

    @Override
    public void saveMessage(AppLeaveMessageDTO dto) {
        LeaveMessageEntity entity = BeanUtil.newBean(dto, LeaveMessageEntity.class);
        IPAddressVO addressVO = IPUtils.getIpAddressByTencent(null);
        entity.setIp(addressVO != null ? addressVO.getIp() : null);
        entity.setOs(addressVO != null ? addressVO.getDevice() : null);
        entity.setIpAddress(addressVO != null ? addressVO.getFullLocation() : null);
        this.save(entity);
    }


    public LeaveMessageEntity checkExist(Long id){
        LeaveMessageEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}