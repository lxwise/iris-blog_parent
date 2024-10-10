package com.iris.blog.exception;

import com.iris.blog.common.R;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.iris.blog.common.ResultCode.*;

/**
 * @author lstar
 * @create 2023-04
 * @description: 全局异常处理类
 */
@ControllerAdvice(basePackages = "com.iris")
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 业务异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public R BusinessExceptionHandler(BusinessException ex) {
        if (ex.getCode() != -1) {
            logger.error("code : " + ex.getCode() + " msg : " + ex.getMessage(), ex);
        }
        if(StringUtils.isBlank(ex.getLocalizedMessage())||StringUtils.isBlank(ex.getMessage())){
            return R.error(ERROR);
        }
        return R.error(ERROR);
    }

    // Assert业务异常
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public R AssertExceptionHandler(IllegalArgumentException ex) {
        logger.error( " msg : " + ex.getMessage(), ex);
        if(StringUtils.isBlank(ex.getLocalizedMessage())){
            return R.error(ERROR);
        }
        return R.error(ex.getMessage());
    }

    // java异常异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R ExceptionHandler(Exception ex) {
        logger.error( " msg : " + ex.getMessage(), ex);
        if(StringUtils.isBlank(ex.getLocalizedMessage())){
            return R.error(ERROR);
        }
        return R.error(ERROR_DEFAULT.getDesc());
    }
}
