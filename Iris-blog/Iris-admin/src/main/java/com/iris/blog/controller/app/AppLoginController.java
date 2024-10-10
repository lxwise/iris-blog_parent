package com.iris.blog.controller.app;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.iris.blog.common.R;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.config.properties.GiteeConfigProperties;
import com.iris.blog.config.properties.GithubConfigProperties;
import com.iris.blog.config.properties.QqConfigProperties;
import com.iris.blog.config.properties.WeiboConfigProperties;
import com.iris.blog.domain.dto.LoginDTO;
import com.iris.blog.domain.dto.app.AppRegisterDTO;
import com.iris.blog.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGiteeScope;
import me.zhyd.oauth.enums.scope.AuthGithubScope;
import me.zhyd.oauth.enums.scope.AuthWeiboScope;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author lstar
 * @create 2024-05
 * @description: 登录-API
 */
@Api(tags = "登录-API")
@RequestMapping("/oauth")
@RestController
public class AppLoginController {

    @Resource
    private  GiteeConfigProperties giteeConfigProperties;
    @Resource
    private  QqConfigProperties qqConfigProperties;
    @Resource
    private  WeiboConfigProperties weiboConfigProperties;
    @Resource
    private  GithubConfigProperties githubConfigProperties;
    @Resource
    private LoginService loginService;

    @ApiOperation(value = "用户登录", httpMethod = "POST", response = R.class, notes = "用户登录")
    @PostMapping("/login")
    public R login(@Validated @RequestBody LoginDTO dto) {
        return loginService.loginWeb(dto);
    }

    @SaCheckLogin
    @ApiOperation(value = "用户退出", httpMethod = "GET", response = R.class, notes = "用户退出")
    @GetMapping("/logout")
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }

    @RLimit(count = 1, time = 60)
    @ApiOperation(value = "发送邮箱验证码", httpMethod = "GET", response = R.class, notes = "发送邮箱验证码")
    @GetMapping("/code")
    public R sendCode(@NotNull(message = "邮箱不能为空") String email) {
        loginService.sendCode(email);
        return R.ok();
    }

    @RLimit(count = 3, time = 7200)
    @ApiOperation(value = "用户邮箱注册", httpMethod = "POST", response = R.class, notes = "用户邮箱注册")
    @PostMapping("/register")
    public R register(@Validated @RequestBody AppRegisterDTO dto) {
        loginService.register(dto);
        return R.ok();
    }

    @RLimit(count = 3, time = 60)
    @PostMapping(value = "/forget/password")
    @ApiOperation(value = "忘记密码", httpMethod = "POST", response = R.class, notes = "忘记密码")
    public R forgetPassword(@Valid @RequestBody AppRegisterDTO dto){
        return loginService.forgetPassword(dto);
    }

    /**
     * 通过JustAuth的AuthRequest拿到第三方的授权链接，并跳转到该链接页面
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/render/{source}")
    public R renderAuth(HttpServletResponse response, @PathVariable String source) throws IOException {
        AuthRequest authRequest = getAuthRequest(source);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return R.ok(authorizeUrl);
    }

    /**
     * 用户在确认第三方平台授权（登录）后， 第三方平台会重定向到该地址，并携带code、state等参数
     * authRequest.login通过code向第三方请求用户数据
     *
     * @param callback 第三方回调时的入参
     * @return 第三方平台的用户信息
     */
    @RequestMapping("/callback/{source}")
    public void login(AuthCallback callback, @PathVariable String source, HttpServletResponse httpServletResponse){
        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);
        loginService.authLogin(response,source,httpServletResponse);
    }

    /**
     * 创建授权request
     *
     * @return AuthRequest
     */
    private AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source) {
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(giteeConfigProperties.getAppId())
                        .clientSecret(giteeConfigProperties.getAppSecret())
                        .redirectUri(giteeConfigProperties.getRedirectUrl())
                        .scopes(Collections.singletonList(AuthGiteeScope.USER_INFO.getScope()))
                        .build());
                break;
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder()
                        .clientId(qqConfigProperties.getAppId())
                        .clientSecret(qqConfigProperties.getAppSecret())
                        .redirectUri(qqConfigProperties.getRedirectUrl())
                        .build());
                break;
            case "weibo":
                authRequest = new AuthWeiboRequest(AuthConfig.builder()
                        .clientId(weiboConfigProperties.getAppId())
                        .clientSecret(weiboConfigProperties.getAppSecret())
                        .redirectUri(weiboConfigProperties.getRedirectUrl())
                        .scopes(Arrays.asList(
                                AuthWeiboScope.EMAIL.getScope(),
                                AuthWeiboScope.FRIENDSHIPS_GROUPS_READ.getScope(),
                                AuthWeiboScope.STATUSES_TO_ME_READ.getScope()
                        ))
                        .build());
                break;
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId(githubConfigProperties.getAppId())
                        .clientSecret(githubConfigProperties.getAppSecret())
                        .redirectUri(githubConfigProperties.getRedirectUrl())
                        .scopes(Arrays.asList(AuthGithubScope.USER.getScope()))
                        .build());
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException("授权地址无效");
        }
        return authRequest;
    }

}

