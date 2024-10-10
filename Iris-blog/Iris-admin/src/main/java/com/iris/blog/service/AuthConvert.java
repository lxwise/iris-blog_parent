package com.iris.blog.service;

import cn.hutool.core.collection.CollUtil;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.common.enums.MenuTypeEnum;
import com.iris.blog.dao.entity.MenuEntity;
import com.iris.blog.dao.entity.RoleEntity;
import com.iris.blog.dao.entity.UserInfoEntity;
import com.iris.blog.domain.vo.AuthPermissionInfoVO;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.utils.LambdaUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;
import java.util.*;

import static com.iris.blog.common.constant.CommonConstant.ID_ROOT;


@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    default AuthPermissionInfoVO convert(UserInfoEntity user, List<RoleEntity> roleList, List<MenuEntity> menuList) {
        return AuthPermissionInfoVO.builder()
                .user(BeanUtil.toBean(user, AuthPermissionInfoVO.UserVO.class))
                .roles(LambdaUtil.convertSet(roleList, RoleEntity::getCode))
                // 权限标识信息
                .permissions(LambdaUtil.convertSet(menuList, MenuEntity::getPermission))
                // 菜单树
                .menus(buildMenuTree(menuList))
                .build();
    }

    AuthPermissionInfoVO.MenuVO convertTreeNode(MenuEntity menu);

    /**
     * 将菜单列表，构建成菜单树
     *
     * @param menuList 菜单列表
     * @return 菜单树
     */
    default List<AuthPermissionInfoVO.MenuVO> buildMenuTree(List<MenuEntity> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        // 移除按钮
        menuList.removeIf(menu -> menu.getType().equals(MenuTypeEnum.BUTTON.getType()));
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(MenuEntity::getSort));

        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<Long, AuthPermissionInfoVO.MenuVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getId(), AuthConvert.INSTANCE.convertTreeNode(menu)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(ID_ROOT)).forEach(childNode -> {
            // 获得父节点
            AuthPermissionInfoVO.MenuVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                        childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return LambdaUtil.filterList(treeNodeMap.values(), node -> ID_ROOT.equals(node.getParentId()));
    }
}
