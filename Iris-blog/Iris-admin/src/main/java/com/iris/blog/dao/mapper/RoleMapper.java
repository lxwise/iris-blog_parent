package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.RoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色表 
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 根据登录id查询这个账号id拥有的角色列表
     * @param loginId
     * @return
     */
    List<String> getRoleListByUserId(Object loginId);

    /**
     * 保存角色权限
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    void saveBatchByRole(@Param("roleId") Long roleId, @Param("list") List<Integer> menuIds);
}
