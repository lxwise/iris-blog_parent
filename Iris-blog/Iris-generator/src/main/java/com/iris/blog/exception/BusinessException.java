package com.iris.blog.exception;

import com.iris.blog.common.ResultCode;
import lombok.Data;

import static com.iris.blog.common.ResultCode.ERROR;
import static com.iris.blog.common.ResultCode.ERROR_DEFAULT;

/**
 * @author lstar
 * @create 2023-04
 * @description: 异常返回类
 */
@Data
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /** 异常代码 */
    protected Integer code;

    /** 异常消息 */
    protected String message;

    public BusinessException() {
        super();
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getDesc());
        this.code = resultCode.getCode();
        this.message = resultCode.getDesc();
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ERROR_DEFAULT.getCode();
        this.message = msg;
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public BusinessException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.message = msg;
    }

    public BusinessException(Throwable e) {
        super(e);
        this.code = ERROR.getCode();
        this.message = e.getMessage();
    }
    public BusinessException(String msg, Throwable e) {
        super(e);
        this.code = ERROR.getCode();
        this.message = e.getMessage();
    }

    @Override
    public String toString() {
        return "errorCode: " + code + ", message: " + message;
    }
}
