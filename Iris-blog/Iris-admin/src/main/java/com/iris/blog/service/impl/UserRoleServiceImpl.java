package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.UserRoleMapper;
import com.iris.blog.dao.entity.UserRoleEntity;
import com.iris.blog.service.UserRoleService;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户角色关联表 
 */
@Service("userRoleService")
@Slf4j
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {
    @Override
    public Long getRoleIdByUserId(Long userId) {

        LambdaQueryWrapper<UserRoleEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserRoleEntity::getUserId, userId).select(UserRoleEntity::getRoleId);
        UserRoleEntity userRole = this.getOne(queryWrapper);

        return Optional.ofNullable(userRole).map(UserRoleEntity::getRoleId).orElse(0L);
    }

    public UserRoleEntity checkExist(Long id){
        UserRoleEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}