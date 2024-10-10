package com.iris.blog.common;


/**
 * 响应码枚举
 */
public enum ResultCode {
    //成功
    SUCCESS( 200, "成功" ),
    //失败
    FAILURE( 400, "失败" ),


    // 系统级别错误码
    ERROR(-1, "操作异常"),
    ERROR_DEFAULT(500,"系统繁忙，请稍后重试"),
    NOT_LOGIN(401, "当前会话已过期，请重新登录"),
    NO_PERMISSION(-7,"无权限"),
    ERROR_PASSWORD(-8,"用户帐号或者密码错误!"),
    DISABLE_ACCOUNT(-12,"该账号已被管理员禁止登录!"),


    // 服务层面
    ERROR_EXCEPTION_MOBILE_CODE(10003,"验证码不正确或已过期，请重新输入"),
    ;

    public int code;
    public String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
