package com.iris.blog.config.satoken;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.mapper.MenuMapper;
import com.iris.blog.dao.mapper.RoleMapper;
import com.iris.blog.service.MenuService;
import com.iris.blog.service.RoleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author lstar
 * @create 2023-04
 * @description: saToken自定义权限验证接口扩展
 */
@Component
public class SaTokenCustomPermission implements StpInterface {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;

    /**
     * 返回一个账号所拥有的权限码集合
     * @param loginId 登录id
     * @param loginType 登录类型(admin,pc,app)
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 从数据库查询这个账号id拥有的权限列表
        String key = RedisKeyConstant.USER_ROLE_MENU_KEY + loginId;
        // 尝试从Redis获取菜单列表
        Object value = redisUtil.getValue(key);
        if (value instanceof List) {
            // 安全地转换为List<String>
            @SuppressWarnings("unchecked")
            List<String> menuList = (List<String>) value;
            return menuList;
        }

        // 如果Redis中没有，从数据库查询
        List<String> menuListByUserId = menuService.getMenuListByUserId(loginId);
        if (menuListByUserId != null && !menuListByUserId.isEmpty()) {
            // 查询成功，保存至Redis并返回结果
            redisUtil.saveRedisValue(key, menuListByUserId, 60 * 60 * 24L);
        }
        return menuListByUserId;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     * @param loginId 登录id
     * @param loginType 登录类型(admin,pc,app)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
            // 从数据库查询这个账号id拥有的角色列表
        String key = RedisKeyConstant.USER_ROLE_KEY + loginId;
        // 尝试从Redis获取角色列表
        Object value = redisUtil.getValue(key);
        if (value instanceof List) {
            // 安全地转换为List<String>
            @SuppressWarnings("unchecked")
            List<String> roleList = (List<String>) value;
            return roleList;
        }
        // 如果Redis中没有，从数据库查询
        List<String> roleListByUserId = roleService.getRoleListByUserId(loginId);
        if (roleListByUserId != null && !roleListByUserId.isEmpty()) {
            // 查询成功，保存至Redis并返回结果
            redisUtil.saveRedisValue(key, roleListByUserId, 60 * 60 * 24L);
        }
        return roleListByUserId;

    }
}
