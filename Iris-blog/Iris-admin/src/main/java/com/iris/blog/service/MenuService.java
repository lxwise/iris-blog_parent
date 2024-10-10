package com.iris.blog.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.MenuEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.MenuDTO;
import com.iris.blog.domain.search.SearchMenuDTO;
import com.iris.blog.domain.vo.MenuSimpleVO;
import com.iris.blog.domain.vo.MenuVO;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 菜单权限表
 */
public interface MenuService extends IService<MenuEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectMenuList(SearchMenuDTO req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectMenuById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveMenu(MenuDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateMenu(MenuDTO dto);

    /**
     * 删除
     * @param id
     * @return
     */
    R removeMenuByIds(Long id);

    /**
     * 获取菜单下拉列表
     * @return
     */
    R<List<MenuSimpleVO>> getSelectMenuList();

    List<String> getMenuListByUserId(Object loginId);

    /**
     * 获得菜单数组
     *
     * @param ids 菜单编号数组
     * @return 菜单数组
     */
    List<MenuEntity> getMenuList(Collection<Long> ids);
}

