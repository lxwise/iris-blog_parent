package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.UserInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户信息表
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {
	
}
