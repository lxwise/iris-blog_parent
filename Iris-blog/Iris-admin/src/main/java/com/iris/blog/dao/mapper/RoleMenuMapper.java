package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.RoleMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色-权限关联表 
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {
	
}
