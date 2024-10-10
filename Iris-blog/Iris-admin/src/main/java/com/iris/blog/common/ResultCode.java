package com.iris.blog.common;


/**
 * 响应码枚举
 */
public enum ResultCode {
    //成功
    SUCCESS( 200, "成功" ),
    //失败
    FAILURE( 400, "操作失败失败" ),


    // 系统级别错误码
    ERROR(-1, "操作异常"),
    NO_DATA_ERROR(-2, "数据不存在,请重试"),
    NO_PERMISSION(-3,"您无操作权限"),
    VISIT_LIMIT_ERROR(-6,"亲~,您太热情了,请稍后再试噢~"),
    PARAM_ERROR(-12,"参数错误"),
    NOT_LOGIN(401, "当前会话已过期，请重新登录"),
    ERROR_DEFAULT(500,"服务器去旅行了，请稍后重试"),



    // 服务层面 1000开头--系统错误码

    CAPTCHA_CODE_ERROR(10001,"验证码错误或已过期"),
    CAPTCHA_CODE_VERIFY_ERROR(10002,"验证失败，请控制拼图对齐缺口"),
    PASSWORD_OR_ACCOUNT_WRONG(10003,"用户名或密码错误"),
    USER_IS_DISABLED(10004,"用户已被禁用"),
    USER_IS_EXIST(10005,"该用户名已被注册"),
    SCHEDULE_JOB_ERROR(10006,"操作定时任务异常"),
    REPORT_EXPORT_ERROR(10007,"报表导出失败"),
    NAME_IS_EXIST(10008,"该名称已存在"),
    NAME_IS_EXIST_RESOURCE(10009,"该名称下还有关联资源,请删除资源后再重试"),
    FILE_UPLOAD_ERROR(10010, "文件上传失败"),
    FILE_DELETE_ERROR(10011, "文件删除失败"),
    PASSWORD_NOT_NULL(10012, "密码不能为空"),
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
