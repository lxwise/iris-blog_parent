package com.iris.blog.common.annotation;


import com.iris.blog.common.resolver.FillEnumMessageResolver;

import java.lang.annotation.*;

/**
 * 对象的属性（Field）和方法（Method），如果持有 {@link FillEnumMessage @FillEnumMessage} 注解，
 * 通过 {@link FillEnumMessageResolver#fill(Object)} 方法进行填充。
 * 持有 {@link FillEnumMessage @FillEnumMessage} 注解的方法只能有一个 String 类型的参数。
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FillEnumMessage {

    /**
     * 用于指定当前类或其父类中的枚举字段名。
     *
     * @return
     */
    String enumFieldName() default "";

    /**
     * 用于指定枚举字段的 Class 类型
     *
     * @return
     */
    Class<? extends Enum> enumFieldType() default Enum.class;

    /**
     * 该属性的作用见 {EnumMessageSource#getMessage(Enum, String) EnumMessageSource.getMessage(Enum, String)}
     *
     * @return
     */
    String fileName() default "";
}
