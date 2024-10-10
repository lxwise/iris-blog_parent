package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.RoleMenuMapper;
import com.iris.blog.dao.entity.RoleMenuEntity;
import com.iris.blog.service.RoleMenuService;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色-权限关联表 
 */
@Service("roleMenuService")
@Slf4j
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuEntity> implements RoleMenuService {

    @Override
    public void deleteByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenuEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleMenuEntity::getRoleId,id);
        this.remove(queryWrapper);
    }

    @Override
    public List<Long> getCurrentUserRoleMenu(Long roleId) {
        LambdaQueryWrapper<RoleMenuEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleMenuEntity::getRoleId,roleId).select(RoleMenuEntity::getMenuId);
        List<RoleMenuEntity> list = this.list(queryWrapper);
        if(!list.isEmpty()){
            return list.stream().map(RoleMenuEntity::getMenuId).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteRolePermission(Long menuId) {
        this.remove(new LambdaQueryWrapper<RoleMenuEntity>().eq(RoleMenuEntity::getMenuId, menuId));

    }

    public RoleMenuEntity checkExist(Long id){
        RoleMenuEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}