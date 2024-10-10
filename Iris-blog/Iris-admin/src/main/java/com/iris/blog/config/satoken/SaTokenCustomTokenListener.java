package com.iris.blog.config.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.hutool.core.util.ObjectUtil;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.service.UserInfoService;
import com.iris.blog.service.UserService;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


/**
 * @author lstar
 * @create 2023-04
 * @description: SaToken自定义侦听器的实现
 */
@Component
@Slf4j
public class SaTokenCustomTokenListener implements SaTokenListener {

    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        log.info("用户已登录,useId:{},token:{}", loginId, tokenValue);
//        try {
//            //每次登录更新用户登录信息
//            //获取用户ip信息
            IPAddressVO addressVO = IPUtils.getIpAddressByTencent(null);
            if(ObjectUtil.isNull(addressVO)){
                return;
            }
            userService.updateUserLoginInfo(loginId, addressVO);
//
//            //记录在线用户
//            UserInfoEntity userInfo = userInfoService.getById((Long) loginId);
//            if (ObjectUtils.isEmpty(userInfo)) {
//                return;
//            }
//            OnlineUserInfo onlineUserInfo = BeanUtil.newBean(userInfo, OnlineUserInfo.class);
//            onlineUserInfo.setLoginId(loginId);
//            onlineUserInfo.setIp(ipLocation.getIpAddress());
//            onlineUserInfo.setAddress(ipLocation.getFullLocation());
//            onlineUserInfo.setLoginTime(LocalDateTime.now());
//
//            //用户信息存入redis
//            redisUtil.saveRedisValue(RedisKeyConstant.USER_ONLINE_INFO + loginId, onlineUserInfo, 30L, TimeUnit.DAYS);
//            log.info("用户已登录,useId:{},token:{}", loginId, tokenValue);
//        } catch (Exception e) {
//            log.error("记录用户登录信息异常,useId:{},异常信息:{}", loginId, e.toString());
//        }
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        redisUtil.delKey(RedisKeyConstant.USER_ONLINE_INFO + loginId);
        log.info("用户已注销,useId:{},token:{}", loginId, tokenValue);
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        redisUtil.delKey(RedisKeyConstant.USER_ONLINE_INFO + loginId);
        log.info("用户已踢下线,useId:{},token:{}", loginId, tokenValue);
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        redisUtil.delKey(RedisKeyConstant.USER_ONLINE_INFO + loginId);
        log.info("用户已顶下线,useId:{},token:{}", loginId, tokenValue);
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
//        log.info("---------- 自定义侦听器实现 doDisable");
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {
//        log.info("---------- 自定义侦听器实现 doUntieDisable");
    }

    /**
     * 每次二级认证时触发
     */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
//        log.info("---------- 自定义侦听器实现 doOpenSafe");
    }

    /**
     * 每次退出二级认证时触发
     */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {
//        log.info("---------- 自定义侦听器实现 doCloseSafe");
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
//        log.info("---------- 自定义侦听器实现 doCreateSession");
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
//        log.info("---------- 自定义侦听器实现 doLogoutSession");
    }

    /**
     * 每次Token续期时触发
     */
    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {
//        log.info("---------- 自定义侦听器实现 doRenewTimeout");
    }
}