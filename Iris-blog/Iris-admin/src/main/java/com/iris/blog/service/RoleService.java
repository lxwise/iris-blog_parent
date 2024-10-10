package com.iris.blog.service;

import java.util.Collection;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.RoleEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.RoleDTO;
import com.iris.blog.domain.search.SearchRoleDTO;
import com.iris.blog.domain.vo.RoleVO;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色表 
 */
public interface RoleService extends IService<RoleEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectRoleList(PageReq<SearchRoleDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectRoleById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveRole(RoleDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateRole(RoleDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeRoleByIds(List<Long> ids);

    /**
     * 根据登录id查询这个账号id拥有的角色列表
     * @param loginId
     * @return
     */
    List<String> getRoleListByUserId(Object loginId);

    /**
     * 获取当前登录用户所拥有的权限
     * @return
     */
    R getCurrentUserRoleMenu();

    /**
     * 获取该角色所有的权限
     * @param roleId
     * @return
     */
    R getAllMenuRoleId(Long roleId);

    /**
     * 判断是否有管理员
     * @param ids
     * @return
     */
    boolean hasSuperAdmin(Collection<Long> ids);

    /**
     * 从缓存中获取角色
     * @param id
     * @return
     */
    RoleEntity getRoleForCache(Long id);

    /**
     * 获得角色列表
     *
     * @param ids 角色编号数组
     * @return 角色列表
     */
    List<RoleEntity> getRoleList(Collection<Long> ids);

    /**
     * 角色下拉列表
     * @return
     */
    R roleDropList();
}

