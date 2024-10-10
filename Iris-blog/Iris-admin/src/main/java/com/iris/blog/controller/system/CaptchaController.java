package com.iris.blog.controller.system;

import cn.hutool.core.util.StrUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.iris.blog.common.R;
import com.iris.blog.utils.IPUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 管理后台 - 验证码
 */
@Api(tags = "管理后台 - 验证码")
@Validated
@RestController("systemCaptchaController")
@RequestMapping("/system/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @PostMapping({"/get"})
    @ApiOperation(value = "验证码", httpMethod = "POST", response = R.class, notes = "验证码")
    public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
        assert request.getRemoteHost() != null;
        data.setBrowserInfo(IPUtils.getRemoteId());
        return captchaService.get(data);
    }

    @PostMapping("/check")
    @ApiOperation(value = "校验验证码", httpMethod = "POST", response = R.class, notes = "校验验证码")
    public ResponseModel check(@RequestBody CaptchaVO data) {
        data.setBrowserInfo(IPUtils.getRemoteId());
        return captchaService.check(data);
    }


}
