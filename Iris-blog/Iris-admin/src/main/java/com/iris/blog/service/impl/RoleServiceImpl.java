package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.enums.RoleCodeEnum;
import com.iris.blog.domain.search.SearchRoleDTO;
import com.iris.blog.service.RoleMenuService;
import com.iris.blog.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.RoleMapper;
import com.iris.blog.dao.entity.RoleEntity;
import com.iris.blog.domain.dto.RoleDTO;
import com.iris.blog.domain.vo.RoleVO;
import com.iris.blog.service.RoleService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色表 
 */
@Service("roleService")
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public R selectRoleList(PageReq<SearchRoleDTO> req){

        RoleEntity entity = BeanUtil.newBean(req.getAction(), RoleEntity.class);
        Page<RoleEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.<RoleEntity>lambdaQuery().
                        like(StringUtils.isNotBlank(entity.getName()),RoleEntity::getName,entity.getName())
                        .like(StringUtils.isNotBlank(entity.getCode()),RoleEntity::getCode,entity.getCode())
                        .orderByDesc(RoleEntity::getId));

        PageBean<RoleVO> pageBean = PageUtil.pageBean(page, RoleVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectRoleById( Long id){

        RoleEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        RoleVO vo = BeanUtil.newBean(entity, RoleVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveRole(RoleDTO dto){
        RoleEntity entity = BeanUtil.newBean(dto, RoleEntity.class);
        this.save(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateRole(RoleDTO dto){
        this.checkExist(dto.getId());
        RoleEntity entity = BeanUtil.newBean(dto, RoleEntity.class);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeRoleByIds(List<Long> ids){
        this.removeByIds(ids);
        ids.forEach(id -> roleMenuService.deleteByRoleId(id));
        return R.ok();
    }

    @Override
    public List<String> getRoleListByUserId(Object loginId) {
        return this.baseMapper.getRoleListByUserId(loginId);
    }

    @Override
    public R getCurrentUserRoleMenu() {

       Long roleId = userRoleService.getRoleIdByUserId(StpUtil.getLoginIdAsLong());
       //权限集合
       List<Long> list = roleMenuService.getCurrentUserRoleMenu(roleId);
       return R.ok(list);
    }

    @Override
    public R getAllMenuRoleId(Long roleId) {
        List<Long> list = roleMenuService.getCurrentUserRoleMenu(roleId);
        return R.ok(list);
    }

    @Override
    public boolean hasSuperAdmin(Collection<Long> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }
        return ids.stream().anyMatch(id -> {
            RoleEntity role = this.getById(id);
            return role != null && RoleCodeEnum.isSuperAdmin(role.getCode());
        });
    }

    @Override
    public RoleEntity getRoleForCache(Long id) {
        return this.getById(id);
    }

    @Override
    public List<RoleEntity> getRoleList(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return baseMapper.selectBatchIds(ids);
    }

    @Override
    public R roleDropList() {

        List<RoleEntity> list = this.list();
        if(CollectionUtil.isNotEmpty(list)){
            List<RoleVO> roleVOList = BeanUtil.toBean(list, RoleVO.class);
            return R.ok(roleVOList);
        }
        return R.ok(Collections.emptyList());
    }


    public RoleEntity checkExist(Long id){
        RoleEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}