package com.iris.blog.service;

import com.iris.blog.common.R;
import com.iris.blog.domain.vo.AuthPermissionInfoVO;

import java.util.Collection;
import java.util.Set;

/**
 * @author lstar
 * @create 2024-05
 * @description: 权限 Service 接口
 * 提供用户-角色、角色-菜单关联权限处理
 */
public interface PermissionService {

    /**
     * 删除角色权限
     * @param id
     */
    void deleteRolePermission(Long id);

    /**
     * 获得角色拥有的菜单权限
     * @param roleIds
     * @return
     */
    Set<Long> getRoleMenuListByRoleId(Collection<Long> roleIds);

    /**
     * 分配角色菜单权限
     * @param roleId
     * @param menuIds
     */
    void assignRoleMenu(Long roleId, Set<Long> menuIds);

    /**
     * 获得管理员拥有的角色列表
     * @param userId
     * @return
     */
    Set<Long> getUserRoleIdListByUserId(Long userId);

    /**
     * 分配用户角色权限
     * @param userId
     * @param roleIds
     */
    void assignUserRole(Long userId, Set<Long> roleIds);

    /**
     * 获取登录用户的权限信息
     * @return
     */
    R<AuthPermissionInfoVO> getPermissionInfo();

}
