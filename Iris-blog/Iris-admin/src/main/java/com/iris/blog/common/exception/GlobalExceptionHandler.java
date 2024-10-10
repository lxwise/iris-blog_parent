package com.iris.blog.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.iris.blog.common.R;
import com.iris.blog.common.resolver.FillEnumMessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static com.iris.blog.common.ResultCode.*;

/**
 * @author lstar
 * @create 2023-04
 * @description: 全局异常处理类
 */
@ControllerAdvice(basePackages = "com.iris")
@Slf4j
public class GlobalExceptionHandler implements ResponseBodyAdvice<R> {

    @Autowired
    private FillEnumMessageResolver fillEnumMessageResolver;

    @Override
    public boolean supports(MethodParameter returnType, @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getMethod() == null) {
            return false;
        }
        return R.class.isAssignableFrom(returnType.getMethod().getReturnType());
    }


    /**
     * 统一返回结果
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public R beforeBodyWrite(R body, @NotNull MethodParameter returnType, @NotNull MediaType selectedContentType,
                             @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                             @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        if (body == null || body.getResultCode() == null) {
            return body;
        }
        fillEnumMessageResolver.fill(body.getData());
        return body;
    }

    // 业务异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public R BusinessExceptionHandler(BusinessException ex) {

        log.error("code : " + ex.getCode() + " msg : " + ex.getMessage(), ex);
        return R.error(ex.code,ex.message);
    }

    // Assert业务异常
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public R AssertExceptionHandler(IllegalArgumentException ex) {
        log.error( " msg : " + ex.getMessage(), ex);
        if(StringUtils.isBlank(ex.getLocalizedMessage())){
            return R.error(ERROR);
        }
        return R.error(ex.getMessage());
    }

    // 登录异常
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public R NotLoginExceptionHandler(NotLoginException ex) {
        log.error( " msg : " + ex.getMessage(), ex);
        return R.error(NOT_LOGIN);
    }

    // 权限异常
    @ExceptionHandler(NotPermissionException.class)
    @ResponseBody
    public R NotPermissionExceptionHandler(NotPermissionException ex) {
        log.error( " msg : " + ex.getMessage(), ex);
        return R.error(NO_PERMISSION);
    }
    // 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error( " msg : " + ex.getMessage(), ex);
        String defaultMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return R.error(StringUtils.isBlank(defaultMessage) ? PARAM_ERROR.getDesc() : defaultMessage);
    }

    // java异常异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R ExceptionHandler(Exception ex) {
        log.error( " msg : " + ex.getMessage(), ex);
        if(StringUtils.isBlank(ex.getLocalizedMessage())){
            return R.error(ERROR);
        }
        return R.error(ERROR_DEFAULT.getDesc());
    }
}
