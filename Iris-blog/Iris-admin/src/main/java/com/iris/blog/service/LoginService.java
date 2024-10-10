package com.iris.blog.service;

import com.iris.blog.common.R;
import com.iris.blog.domain.dto.CaptchaDTO;
import com.iris.blog.domain.dto.LoginDTO;
import com.iris.blog.domain.dto.app.AppRegisterDTO;
import me.zhyd.oauth.model.AuthResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 登录-接口
 */
public interface LoginService {

    R login(LoginDTO dto);

    R getCaptcha(CaptchaDTO dto);

    R loginWeb(LoginDTO dto);

    void register(AppRegisterDTO dto);

    R forgetPassword(AppRegisterDTO dto);

    void authLogin(AuthResponse response, String source, HttpServletResponse httpServletResponse);

    void sendCode(String email);
}
