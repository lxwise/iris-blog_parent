package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
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
import com.iris.blog.dao.mapper.UserInfoMapper;
import com.iris.blog.dao.entity.UserInfoEntity;
import com.iris.blog.domain.dto.UserInfoDTO;
import com.iris.blog.domain.vo.UserInfoVO;
import com.iris.blog.service.UserInfoService;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户信息表
 */
@Service("userInfoService")
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements UserInfoService {

    @Override
    public R selectUserInfoList(PageReq<UserInfoDTO> req){

        UserInfoEntity entity = BeanUtil.newBean(req.getAction(), UserInfoEntity.class);
        Page<UserInfoEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(UserInfoEntity::getId));

        PageBean<UserInfoVO> pageBean = PageUtil.pageBean(page, UserInfoVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectUserInfoById( Long id){

        UserInfoEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        UserInfoVO vo = BeanUtil.newBean(entity, UserInfoVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveUserInfo(UserInfoDTO dto){
        UserInfoEntity entity = BeanUtil.newBean(dto, UserInfoEntity.class);
        this.save(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateUserInfo(UserInfoDTO dto){
        this.checkExist(dto.getId());
        UserInfoEntity entity = BeanUtil.newBean(dto, UserInfoEntity.class);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeUserInfoByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    public UserInfoEntity checkExist(Long id){
        UserInfoEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}