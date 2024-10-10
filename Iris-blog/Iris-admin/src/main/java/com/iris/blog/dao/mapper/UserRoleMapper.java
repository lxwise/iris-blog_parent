package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.UserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户角色关联表 
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {
	
}
