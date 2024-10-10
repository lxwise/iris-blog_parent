package com.iris.blog.common.excel;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

 /**
 * @author lstar
 * @create 2023-04
 * @description: 对excel导入时，处理枚举转化
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumFiledConvert {
 
 
    /**
     * 枚举映射map  key-value,key-value,key-value,key-value
     * @return
     */
    String enumMap() default "";
 
    /**
     * 枚举类导入、导出在excel中的分隔符号
     * @return
     */
    String spiteChar() default ",";
 
    /**
     * 单选 or 多选
     * @return
     */
    boolean single() default true;
}