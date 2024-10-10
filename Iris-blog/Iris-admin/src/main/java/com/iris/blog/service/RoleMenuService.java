package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.RoleMenuEntity;
/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色-权限关联表 
 */
public interface RoleMenuService extends IService<RoleMenuEntity> {


    /**
     * 根据角色id删除关联权限
     * @param id
     */
    void deleteByRoleId(Long id);

    /**
     * 根据角色id获取当前角色关联的权限
     * @param roleId
     * @return
     */
    List<Long> getCurrentUserRoleMenu(Long roleId);

    /**
     * 删除角色权限
     * @param menuId
     */
    void deleteRolePermission(Long menuId);
}

