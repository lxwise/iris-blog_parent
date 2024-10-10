package com.iris.blog.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公共响应参数
 */
@Getter
@Setter
@ToString
@ApiModel(description = "公共响应参数")
public class R<T> {

    /**
     * 请求是否成功［true：成功；false：失败］
     */
    @ApiModelProperty(value = "请求是否成功", dataType = "boolean", example = "true")
    private Boolean success;

    /**
     * 返回结果码
     */
    @ApiModelProperty(value = "返回结果码", position = 1, dataType = "int", example = "SUCCESS")
    private Integer code;

    /**
     * 返回结果描述
     */
    @ApiModelProperty(value = "返回结果描述", position = 2, dataType = "string", example = "成功")
    private String msg;

    /**
     * 返回的VO对象
     */
    @ApiModelProperty(value = "返回的VO对象", position = 3)
    private T data;

    @JsonIgnore
    private ResultCode resultCode;

    /**
     * 构造“成功”的响应对象
     */
    public R() {
        this(true, ResultCode.SUCCESS);
    }

    /**
     * 构造“成功”的响应对象
     *
     * @param data 返回的VO对象
     */
    public R(T data) {
        this();
        this.data = data;
    }

    /**
     * 构造响应对象
     *
     * @param success    请求是否成功［true：成功；false：失败］
     * @param resultCode 结果码
     */
    public R(boolean success, ResultCode resultCode) {
        this.success = success;
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
        this.msg = resultCode.getDesc();
    }

    /**
     * @return “成功”的响应对象
     */
    public static <T> R<T> ok() {
        return new R();
    }

    /**
     * @return “成功”的响应对象
     */
    public static <T> R<T> ok(T o) {
        return new R(o);
    }

    /**
     * @return “失败”的响应对象
     */
    public static <T> R<T> error() {
        return new R(false, ResultCode.FAILURE);
    }

//    /**
//     * @param resultCode 结果码
//     * @return “失败”的响应对象
//     */
//    public static<T> R<T> error(ResultCode resultCode) {
//        return new R(false, resultCode);
//    }


    /**
     * @param info 内容
     * @return “失败”的响应对象
     */
    public static <T> R<T> error(String info) {
        R<T> r = new R<>(null);
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR.getCode());
        r.setMsg(info);
        return r;
    }
    /**
     * @param code code码
     * @param info 内容
     * @return “失败”的响应对象
     */
    public static <T> R<T> error(Integer code,String info) {
        R<T> r = new R<>(null);
        r.setSuccess(false);
        r.setCode(code);
        r.setMsg(info);
        return r;
    }
    /**
     * @param resultCode 结果码
     * @return “失败”的响应对象
     */
    public static <T> R<T> error(ResultCode resultCode) {
        R<T> r = new R<>(null);
        r.setSuccess(false);
        r.setCode(resultCode.getCode());
        r.setMsg(resultCode.getDesc());
        return r;
    }
}
