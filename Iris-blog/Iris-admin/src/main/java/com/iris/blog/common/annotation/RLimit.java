package com.iris.blog.common.annotation;

import com.iris.blog.common.ResultCode;

import java.lang.annotation.*;

/**
 * 自定义注解  限流
 * 注解放置的目标位置即方法级别
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RLimit {


    /**
     * 在时间窗内的限流次数
     * @return
     */
    public int count() default 10;

    /**
     * 限流时间窗
     * @return
     */
    public int time() default 10;

    /**
     * 提示信息
     * @return
     */
    public String msg() default "亲~,您太热情了,请稍后再试噢~";
}
