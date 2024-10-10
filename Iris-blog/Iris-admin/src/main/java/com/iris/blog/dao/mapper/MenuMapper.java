package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.MenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 菜单权限表
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuEntity> {

    /**
     * 根据登录id查询这个账号id拥有的权限列表
     * @param loginId
     * @return
     */
    List<String> getMenuListByUserId(Object loginId);
}
