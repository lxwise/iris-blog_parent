package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.constant.CommonConstant;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.common.enums.LoginTypeEnum;
import com.iris.blog.common.enums.RoleCodeEnum;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.OperateLogEntity;
import com.iris.blog.dao.entity.UserEntity;
import com.iris.blog.dao.entity.UserInfoEntity;
import com.iris.blog.dao.entity.UserRoleEntity;
import com.iris.blog.domain.dto.CaptchaDTO;
import com.iris.blog.domain.dto.LoginDTO;
import com.iris.blog.domain.dto.app.AppRegisterDTO;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.domain.vo.SessionUserVO;
import com.iris.blog.domain.vo.SiteConfigVO;
import com.iris.blog.service.*;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.utils.CaptchaUtil;
import com.iris.blog.utils.EncryptUtil;
import com.iris.blog.utils.IPUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author lstar
 * @date: 2024-04
 * @description:
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserService userService;
    @Resource
    private CaptchaUtil captchaUtil;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private OperateLogService operateLogService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SiteConfigService siteConfigService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private EmailService emailService;
    @Override
    public R getCaptcha(CaptchaDTO dto) {
        return R.ok(captchaUtil.getCaptcha(dto));
    }

    @Override
    public R loginWeb(LoginDTO dto) {
        //校验验证码
        verifyCode(dto.getUsername(), dto.getCaptchaCode(),Boolean.TRUE);
        UserEntity user = getUser(dto);
        //七天有效时间  登录
        StpUtil.login(user.getId(), 60 * 60 * 24 * 7L);
        return R.ok(StpUtil.getTokenValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(AppRegisterDTO dto) {

        verifyCode(dto.getUsername(), dto.getCode(),Boolean.FALSE);
        UserEntity user = userService.getOne(new LambdaQueryWrapper<UserEntity>()
                .select(UserEntity::getUsername)
                .eq(UserEntity::getUsername, dto.getUsername()));
        Assert.isNull(user, "邮箱已注册！");
        SiteConfigVO siteConfig = siteConfigService.getSiteConfigInfo();
        // 添加用户
        UserEntity entity = BeanUtil.newBean(dto, UserEntity.class);
        if(StringUtils.isBlank(entity.getPassword())){
            entity.setPassword(EncryptUtil.encrypt(CommonConstant.DEFAULT_PASSWORD,CommonConstant.KEY));
        }else {
            entity.setPassword(EncryptUtil.encrypt(entity.getPassword(),CommonConstant.KEY));
        }
        IPAddressVO addressVO = IPUtils.getIpAddressByTencent(null);
        entity.setLoginType(LoginTypeEnum.EMAIL.getType());
        entity.setIp(addressVO != null ? addressVO.getIp() : null);
        entity.setIpAddress(addressVO != null ? addressVO.getFullLocation() : null);
        entity.setProvince(addressVO != null ? addressVO.getProvince() : null);
        entity.setCity(addressVO != null ? addressVO.getCity() : null);
        entity.setLatitude(addressVO != null ? addressVO.getLatitude() : null);
        entity.setLongitude(addressVO != null ? addressVO.getLongitude() : null);
        entity.setOs(addressVO != null ? addressVO.getDevice() : null);
        entity.setLastLoginTime(LocalDateTime.now());
        this.userService.save(entity);

        //保存用户信息
        UserInfoEntity userInfo = BeanUtil.newBean(dto, UserInfoEntity.class);
        userInfo.setId(entity.getId());
        userInfo.setEmail(dto.getUsername());
        userInfo.setNickname("用户"+ RandomUtil.randomNumbers(6));
        userInfo.setAvatar(siteConfig.getUserAvatar());
        userInfoService.save(userInfo);
        // 绑定用户角色
        UserRoleEntity userRole = UserRoleEntity.builder()
                .userId(entity.getId())
                .roleId(RoleCodeEnum.COMMON.getRoleId())
                .build();
        userRoleService.save(userRole);
    }

    @Override
    public R forgetPassword(AppRegisterDTO dto) {
        verifyCode(dto.getUsername(), dto.getCode(),Boolean.TRUE);
        UserEntity existUser = userService.getOne(new LambdaQueryWrapper<UserEntity>()
                .select(UserEntity::getUsername)
                .eq(UserEntity::getUsername, dto.getUsername()));
        Assert.notNull(existUser, "邮箱尚未注册！");
        // 根据用户名修改密码
        userService.update(Wrappers.<UserEntity>lambdaUpdate()
                .set(UserEntity::getPassword, EncryptUtil.encrypt(dto.getPassword(),CommonConstant.KEY))
                .eq(UserEntity::getUsername, dto.getUsername()));

        return R.ok();
    }

    /**
     * 第三方登录授权之后的逻辑
     *
     * @param response
     * @param source
     * @param httpServletResponse
     */
    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authLogin(AuthResponse response, String source, HttpServletResponse httpServletResponse) {
        String result = JSONObject.toJSONString(response.getData());
        log.info("第三方登录验证结果:{}", result);

        if(StringUtils.isBlank(result)){
            throw new BusinessException("第三方登录失败");
        }
        JSONObject jsonObject = JSON.parseObject(result);
        if(Objects.isNull(jsonObject)){
            throw new BusinessException("第三方登录失败");
        }
        Object uuid = jsonObject.get("uuid");
        // 获取用户ip信息
        IPAddressVO addressVO = IPUtils.getIpAddressByTencent(null);

        SiteConfigVO siteConfig = siteConfigService.getSiteConfigInfo();

        // 判断是否已注册
        UserEntity entity = userService.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername, uuid));
        if (Objects.nonNull(entity)) {
            // 更新登录信息
            userService.update(new UserEntity(), new LambdaUpdateWrapper<UserEntity>()
                            .set(UserEntity::getIp,addressVO != null ? addressVO.getIp() : null)
                            .set(UserEntity::getProvince,addressVO != null ? addressVO.getProvince() : null)
                            .set(UserEntity::getCity,addressVO != null ? addressVO.getCity() : null)
                            .set(UserEntity::getLatitude,addressVO != null ? addressVO.getLatitude() : null)
                            .set(UserEntity::getLongitude,addressVO != null ? addressVO.getLongitude() : null)
                            .set(UserEntity::getOs,addressVO != null ? addressVO.getDevice() : null)
                            .set(UserEntity::getLastLoginTime,LocalDateTime.now())
                            .eq(UserEntity::getId, entity.getId()));
        } else {
            // 获取第三方用户信息，保存到数据库返回
            // 保存用户
            entity = new UserEntity();
            entity.setUsername(uuid.toString());
            entity.setPassword(EncryptUtil.encrypt(CommonConstant.DEFAULT_PASSWORD,CommonConstant.KEY));

            entity.setLoginType(LoginTypeEnum.getType(source));

            entity.setIp(addressVO != null ? addressVO.getIp() : null);
            entity.setProvince(addressVO != null ? addressVO.getProvince() : null);
            entity.setCity(addressVO != null ? addressVO.getCity() : null);
            entity.setLatitude(addressVO != null ? addressVO.getLatitude() : null);
            entity.setLongitude(addressVO != null ? addressVO.getLongitude() : null);
            entity.setOs(addressVO != null ? addressVO.getDevice() : null);
            entity.setLastLoginTime(LocalDateTime.now());
            this.userService.save(entity);

            //保存用户信息
            UserInfoEntity userInfo = BeanUtil.newBean(entity, UserInfoEntity.class);
            userInfo.setId(entity.getId());
            userInfo.setNickname(source.equals("github") ? jsonObject.get("username").toString() : jsonObject.get("nickname").toString());
            if(StringUtils.isBlank(userInfo.getNickname())){
                userInfo.setNickname("用户"+ RandomUtil.randomNumbers(6));
            }
            userInfo.setAvatar(jsonObject.get("avatar").toString());
            userInfoService.save(userInfo);
            // 绑定用户角色
            UserRoleEntity userRole = UserRoleEntity.builder()
                    .userId(entity.getId())
                    .roleId(RoleCodeEnum.COMMON.getRoleId())
                    .build();
            userRoleService.save(userRole);
        }

        //七天有效时间  登录
        StpUtil.login(entity.getId(), 60 * 60 * 24 * 7L);
        httpServletResponse.sendRedirect("https://www.lstar.icu/?token=" + StpUtil.getTokenValue());
    }

    @Override
    public void sendCode(String email) {
        emailService.sendCode(email);
    }

    @NotNull
    private UserEntity getUser(LoginDTO dto) {
        //校验用户名和密码
        UserEntity user = userService.selectUserByName(dto.getUsername());
        Optional.ofNullable(user).orElseThrow(() -> new BusinessException(ResultCode.PASSWORD_OR_ACCOUNT_WRONG));

        //校验用户是否被禁用
        if (!user.getStatus()) {
            throw new BusinessException(ResultCode.USER_IS_DISABLED);
        }
        // 比较密码

        if(!user.getPassword().equals(EncryptUtil.encrypt(dto.getPassword(), CommonConstant.KEY))){
            throw new BusinessException(ResultCode.PASSWORD_OR_ACCOUNT_WRONG);
        }
        return user;
    }

    @Override
    public R login(LoginDTO dto) {
        StopWatch sw = new StopWatch();
        sw.start();
        //校验验证码
//        this.captchaUtil.checkImageCode(dto.getRandomStr(), dto.getCaptchaValue());
        validateCaptcha(dto);
        //校验用户名和密码
        UserEntity user = getUser(dto);

        if (!RoleCodeEnum.SUPER_ADMIN.getUserId().equals(user.getId())){
            throw new BusinessException(ResultCode.NO_PERMISSION);
        }
        if (dto.getRememberMe()) {
            StpUtil.login(user.getId(),new SaLoginModel().setTimeout(60 * 60 * 24 * 7));
        }else {
            StpUtil.login(user.getId(),"system");
        }
        SessionUserVO sessionUser = BeanUtil.newBean(user, SessionUserVO.class);
        StpUtil.getSession().set(CommonConstant.LOGIN_USER,sessionUser);

        //插入登录日志
        saveOperateLog(dto, user, sw);
        return R.ok(StpUtil.getTokenValue());
    }

    private void saveOperateLog(LoginDTO dto, UserEntity user, StopWatch sw) {
        OperateLogEntity entity = new OperateLogEntity();
        entity.setUserId(user.getId());
        entity.setUserName(user.getUsername());
        entity.setRequestUrl("/iris/system/login/login");
        entity.setRequestType("POST");
        entity.setRequestClassName("com.iris.blog.controller.system.LoginController");
        entity.setRequestMethod("login");
        entity.setRequestParams(JSONObject.toJSONString(dto));
        sw.stop();
        entity.setRequestTime(String.valueOf(sw.getLastTaskTimeMillis()));
        entity.setRequestIp(IPUtils.getIp());
        entity.setRequestAddress(IPUtils.getIpAreaAddress(null));
        entity.setOperateDesc("用户登录");
        entity.setOperateOs(IPUtils.getDeviceDetails());
        entity.setLogType(Boolean.FALSE);
        operateLogService.save(entity);
    }

    void validateCaptcha(LoginDTO dto) {
        // 校验验证码
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(dto.getCaptchaCode());
        ResponseModel response = captchaService.verification(captchaVO);
        // 验证不通过
        if (!response.isSuccess()) {
            // 创建登录失败日志（验证码不正确)
            log.error("用户登录，验证码错误，username：{}", dto.getUsername());
            throw new BusinessException(ResultCode.CAPTCHA_CODE_ERROR);
        }
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     */
    public void verifyCode(String username, String code,Boolean isLogin) {
        String redisKey = RedisKeyConstant.USER_LOGIN_CODE + username;
        String sysCode = redisUtil.getKeyValue(redisKey,String.class);
        Optional.ofNullable(sysCode).orElseThrow(() -> new BusinessException(ResultCode.CAPTCHA_CODE_ERROR));
        if(!sysCode.equals(code)){
           throw  new BusinessException(ResultCode.CAPTCHA_CODE_ERROR);
        }
        if(isLogin){
         redisUtil.delKey(redisKey);
        }
    }
}
