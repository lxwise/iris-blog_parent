package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.search.SearchFriendLinkDTO;
import com.iris.blog.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.FriendLinkMapper;
import com.iris.blog.dao.entity.FriendLinkEntity;
import com.iris.blog.domain.dto.FriendLinkDTO;
import com.iris.blog.domain.vo.FriendLinkVO;
import com.iris.blog.service.FriendLinkService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 友情链接表
 */
@Service("friendLinkService")
@Slf4j
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLinkEntity> implements FriendLinkService {

    @Resource
    private EmailService emailService;
    @Override
    public R selectFriendLinkList(PageReq<SearchFriendLinkDTO> req){

        FriendLinkEntity entity = BeanUtil.newBean(req.getAction(), FriendLinkEntity.class);
        Page<FriendLinkEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(FriendLinkEntity::getId));

        PageBean<FriendLinkVO> pageBean = PageUtil.pageBean(page, FriendLinkVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectFriendLinkById( Long id){

        FriendLinkEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        FriendLinkVO vo = BeanUtil.newBean(entity, FriendLinkVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveFriendLink(FriendLinkDTO dto){
        FriendLinkEntity entity = BeanUtil.newBean(dto, FriendLinkEntity.class);
        this.save(entity);
        this.emailService.emailNoticeMe("网站有新的友链接入啦("+entity.getUrl()+")，快去审核吧!!!");
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateFriendLink(FriendLinkDTO dto){
        this.checkExist(dto.getId());
        FriendLinkEntity entity = BeanUtil.newBean(dto, FriendLinkEntity.class);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeFriendLinkByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    public R topFriendLink(Long id) {
        LambdaQueryWrapper<FriendLinkEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(FriendLinkEntity::getSort).orderByDesc(FriendLinkEntity::getSort).last("limit 1");
        FriendLinkEntity entity = this.getOne(queryWrapper);

        LambdaUpdateWrapper<FriendLinkEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(FriendLinkEntity::getSort, entity.getSort() + 1)
                .eq(FriendLinkEntity::getId, id);
        this.update(updateWrapper);
        return R.ok();
    }

    @Override
    public R updateStatus(Long id,Integer status) {
        FriendLinkEntity entity = checkExist(id);
        entity.setStatus(status);
        this.updateById(entity);
        //发送通知
        if(BaseNumberEnum.ONE.getCode().equals(status)){
            this.emailService.friendPassSendEmail(entity.getEmail());
        }else {
            this.emailService.friendFailedSendEmail(entity.getEmail(), "您提交的友链不符合规范,请重新提交!");
        }
        return R.ok();
    }

    @Override
    public R<List<FriendLinkVO>> friendLinkList() {
        List<FriendLinkEntity> list = this.lambdaQuery().eq(FriendLinkEntity::getStatus, BaseNumberEnum.ONE.getCode()).list();
        List<FriendLinkVO> linkVOS = cn.hutool.core.bean.BeanUtil.copyToList(list, FriendLinkVO.class);
        return R.ok(linkVOS);
    }

    public FriendLinkEntity checkExist(Long id){
        FriendLinkEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}