package com.iris.blog.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.dao.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.blog.domain.search.SearchUserDTO;
import com.iris.blog.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户表
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 分页查询用户列表
     *
     * @param page
     * @param action
     * @return
     */
    Page<UserVO> pageList(Page<UserVO> page, @Param("action") SearchUserDTO action);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    UserVO getUserInfoById(Long id);

    UserVO getUserInfoByUserId(@Param("userId") long userId);
}
