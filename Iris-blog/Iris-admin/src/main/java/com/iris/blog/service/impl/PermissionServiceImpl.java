package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iris.blog.common.R;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.dao.entity.*;
import com.iris.blog.service.AuthConvert;
import com.iris.blog.domain.vo.AuthPermissionInfoVO;
import com.iris.blog.service.*;
import com.iris.blog.utils.LambdaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author lstar
 * @create 2024-05
 * @description:
 */

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Resource
    private UserInfoService userInfoService;
    @Override
    public void deleteRolePermission(Long menuId) {
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenuEntity>().eq(RoleMenuEntity::getMenuId, menuId));
    }

    @Override
    public Set<Long> getRoleMenuListByRoleId(Collection<Long> roleIds) {

        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptySet();
        }

        // 如果是管理员的情况下，获取全部菜单编号
        if (roleService.hasSuperAdmin(roleIds)) {
            return LambdaUtil.convertSet(menuService.list(), MenuEntity::getId);
        }
        // 如果是非管理员的情况下，获得拥有的菜单编号
        List<RoleMenuEntity> list = roleMenuService.list(new LambdaQueryWrapper<RoleMenuEntity>().in(RoleMenuEntity::getRoleId, roleIds));
        return LambdaUtil.convertSet(list, RoleMenuEntity::getMenuId);
    }

    @Override
    public void assignRoleMenu(Long roleId, Set<Long> menuIds) {
        // 获得角色拥有菜单id
        List<RoleMenuEntity> list = roleMenuService.list(new LambdaQueryWrapper<RoleMenuEntity>().in(RoleMenuEntity::getRoleId, roleId));
        Set<Long> dbMenuIds = LambdaUtil.convertSet(list, RoleMenuEntity::getMenuId);
        // 计算新增和删除的菜单编号
        Set<Long> menuIdList = CollUtil.emptyIfNull(menuIds);
        Collection<Long> createMenuIds = CollUtil.subtract(menuIdList, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIdList);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (CollUtil.isNotEmpty(createMenuIds)) {
            roleMenuService.saveBatch(LambdaUtil.convertList(createMenuIds, menuId -> {
                RoleMenuEntity entity = new RoleMenuEntity();
                entity.setRoleId(roleId);
                entity.setMenuId(menuId);
                return entity;
            }));
        }
        if (CollUtil.isNotEmpty(deleteMenuIds)) {
            roleMenuService.remove(new LambdaQueryWrapper<RoleMenuEntity>()
                    .eq(RoleMenuEntity::getRoleId, roleId)
                    .in(RoleMenuEntity::getMenuId, deleteMenuIds));
        }
    }

    @Override
    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        List<UserRoleEntity> list = userRoleService.list(new LambdaQueryWrapper<UserRoleEntity>().eq(UserRoleEntity::getUserId, userId));
        return LambdaUtil.convertSet(list,UserRoleEntity::getRoleId);
    }

    @Override
    public void assignUserRole(Long userId, Set<Long> roleIds) {
        // 获得角色拥有角色编号
        List<UserRoleEntity> list = userRoleService.list(new LambdaQueryWrapper<UserRoleEntity>().in(UserRoleEntity::getUserId, userId));
        Set<Long> dbRoleIds = LambdaUtil.convertSet(list, UserRoleEntity::getRoleId);
        // 计算新增和删除的角色编号
        Set<Long> roleIdList = CollUtil.emptyIfNull(roleIds);
        Collection<Long> createRoleIds = CollUtil.subtract(roleIdList, dbRoleIds);
        Collection<Long> deleteRoleIds = CollUtil.subtract(dbRoleIds, roleIdList);
        // 执行新增和删除。对于已经授权的角色，不用做任何处理
        if (!CollectionUtil.isEmpty(createRoleIds)) {
            userRoleService.saveBatch(LambdaUtil.convertList(createRoleIds, roleId -> {
                UserRoleEntity entity = new UserRoleEntity();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteRoleIds)) {
            userRoleService.remove(new LambdaQueryWrapper<UserRoleEntity>()
                    .eq(UserRoleEntity::getUserId, userId)
                    .in(UserRoleEntity::getRoleId, deleteRoleIds));
        }
    }

    @Override
    public R<AuthPermissionInfoVO> getPermissionInfo() {
        // 1.1 获得用户信息
        long userId = StpUtil.getLoginIdAsLong();
        UserInfoEntity user = userInfoService.getById(userId);
        if (user == null) {
            return R.ok();
        }

        // 1.2 获得角色列表
        Set<Long> roleIds = getUserRoleIdListByUserId(userId);
        if (CollUtil.isEmpty(roleIds)) {
            return R.ok(AuthConvert.INSTANCE.convert(user, Collections.emptyList(), Collections.emptyList()));
        }
        List<RoleEntity> roles = roleService.getRoleList(roleIds);
        // 1.3 获得菜单列表
        Set<Long> menuIds = getRoleMenuListByRoleId(LambdaUtil.convertSet(roles, RoleEntity::getId));
        List<MenuEntity> menuList = menuService.getMenuList(menuIds);
        // 移除禁用的菜单
        menuList.removeIf(menu -> BaseNumberEnum.ZERO.getCode().equals(menu.getStatus()));

        // 2. 拼接结果返回
        AuthPermissionInfoVO vo = AuthConvert.INSTANCE.convert(user, roles, menuList);
        return R.ok(vo);
    }
}
