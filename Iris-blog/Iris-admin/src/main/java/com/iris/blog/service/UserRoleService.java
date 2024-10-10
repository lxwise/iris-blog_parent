package com.iris.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.UserRoleEntity;
/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户角色关联表 
 */
public interface UserRoleService extends IService<UserRoleEntity> {

    /**
     * 根据用户id获取角色id
     * @param userId
     * @return
     */
    Long getRoleIdByUserId(Long userId);
}

