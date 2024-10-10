package com.iris.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.common.enums.MenuTypeEnum;
import com.iris.blog.dao.entity.RoleMenuEntity;
import com.iris.blog.domain.search.SearchMenuDTO;
import com.iris.blog.domain.vo.MenuSimpleVO;
import com.iris.blog.service.PermissionService;
import com.iris.blog.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.MenuMapper;
import com.iris.blog.dao.entity.MenuEntity;
import com.iris.blog.domain.dto.MenuDTO;
import com.iris.blog.domain.vo.MenuVO;
import com.iris.blog.service.MenuService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.iris.blog.common.constant.CommonConstant.ID_ROOT;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 菜单权限表
 */
@Service("menuService")
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Resource
    private RoleMenuService roleMenuService;
    @Override
    public R selectMenuList(SearchMenuDTO dto){

        List<MenuEntity> entityList = baseMapper.selectList(new LambdaQueryWrapper<MenuEntity>()
                .like(StringUtils.isNotBlank(dto.getName()), MenuEntity::getName, dto.getName())
                .eq(null != dto.getStatus(), MenuEntity::getStatus, dto.getStatus()));
        entityList.sort(Comparator.comparing(MenuEntity::getSort));
        return R.ok(BeanUtil.toBean(entityList, MenuVO.class));
    }


    @Override
    public R selectMenuById( Long id){

        MenuEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        MenuVO vo = BeanUtil.newBean(entity, MenuVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveMenu(MenuDTO dto){
        // 校验父菜单存在
        checkParentMenu(dto.getParentId(), null);
        // 校验菜单（自己）
        checkMenuExist(dto.getParentId(), dto.getName(), null);
        MenuEntity entity = BeanUtil.newBean(dto, MenuEntity.class);
        initMenu(entity);
        this.save(entity);
        return R.ok(entity.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateMenu(MenuDTO dto){
        this.checkExist(dto.getId());
        // 校验父菜单存在
        checkParentMenu(dto.getParentId(), null);
        // 校验菜单（自己）
        checkMenuExist(dto.getParentId(), dto.getName(), dto.getId());
        MenuEntity entity = BeanUtil.newBean(dto, MenuEntity.class);
        initMenu(entity);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeMenuByIds(Long menuId){
        checkExist(menuId);
        // 校验是否还有子菜单
        if (this.selectCountByParentId(menuId) > 0) {
            throw new BusinessException("存在子菜单，无法删除");
        }
        this.removeById(menuId);
        // 删除角色权限
        roleMenuService.deleteRolePermission(menuId);
        return R.ok();
    }

    @Override
    public R<List<MenuSimpleVO>> getSelectMenuList() {
        List<MenuEntity> entityList = baseMapper.selectList(new LambdaQueryWrapper<MenuEntity>()
                .eq(MenuEntity::getStatus, BaseNumberEnum.ONE.getCode()));
        return R.ok(BeanUtil.toBean(entityList, MenuSimpleVO.class));
    }

    @Override
    public List<String> getMenuListByUserId(Object loginId) {
        return this.baseMapper.getMenuListByUserId(loginId);
    }

    @Override
    public List<MenuEntity> getMenuList(Collection<Long> ids) {
        // 当 ids 为空时，返回一个空的实例对象
        if (CollUtil.isEmpty(ids)) {
            return Lists.newArrayList();
        }
        return this.baseMapper.selectBatchIds(ids);
    }

    /**
     * 校验是否还有子菜单
     * @param id
     * @return
     */
    private Long selectCountByParentId(Long id) {
        return this.count(new LambdaQueryWrapper<MenuEntity>().eq(MenuEntity::getParentId, id));
    }

    public MenuEntity checkExist(Long id){
        MenuEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }

    /**
     *
     * @param parentId 父id
     * @param name 菜单名字
     * @param id id
     */
    private void checkMenuExist(Long parentId, String name, Long id) {
        MenuEntity menu = this.selectByParentIdAndName(parentId, name);
        if (menu == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (id == null) {
            throw new BusinessException("已经存在该名字的菜单");
        }
        if (!menu.getId().equals(id)) {
            throw new BusinessException("已经存在该名字的菜单");
        }
    }

    /**
     * 初始化菜单的通用属性。
     * @param menu 菜单
     */
    private void initMenu(MenuEntity menu) {
        // 菜单为按钮类型时，无需 component、icon、path 属性
        if (MenuTypeEnum.BUTTON.getType().equals(menu.getType())) {
            menu.setComponent("");
            menu.setComponentName("");
            menu.setIcon("");
            menu.setPath("");
        }
    }
    /**
     * 根据父id和菜单名字查询菜单
     * @param parentId
     * @param name
     * @return
     */
    private MenuEntity selectByParentIdAndName(Long parentId, String name) {
        return this.getOne(new LambdaQueryWrapper<MenuEntity>().eq(MenuEntity::getParentId, parentId).eq(MenuEntity::getName, name));
    }

    /**
     * 校验父菜单是否合法 不能设置自己为父菜单 父菜单不存在 父菜单必须是菜单类型
     * @param parentId 父id
     * @param childId 子id
     */
    private void checkParentMenu(Long parentId, Long childId) {
        if (parentId == null || ID_ROOT.equals(parentId)) {
            return;
        }
        // 不能设置自己为父菜单
        if (parentId.equals(childId)) {
            throw new BusinessException("不能设置自己为父菜单");
        }
        MenuEntity menu = this.getById(parentId);
        // 父菜单不存在
        if (menu == null) {
            throw new BusinessException("父菜单不存在");
        }
        // 父菜单必须是目录或者菜单类型
        if (!MenuTypeEnum.DIR.getType().equals(menu.getType())
                && !MenuTypeEnum.MENU.getType().equals(menu.getType())) {
            throw new BusinessException("父菜单的类型必须是目录或者菜单");
        }
    }
}