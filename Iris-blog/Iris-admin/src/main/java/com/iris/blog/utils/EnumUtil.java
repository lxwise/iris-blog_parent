package com.iris.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author lstar
 * @create 2021-04
 * @description: 枚举工具类
 */
@Slf4j
public class EnumUtil {


    /**
     * 判断某个枚举是否包某个code值
     * @param enumClass 需要判断是否存在那个枚举类中
     * @param code 需要判断的值
     * @param propName 枚举类get方法
     * @return 包含返回true，否则返回false
     */
    public static boolean isInclude(Class enumClass, int code, String propName){
        List enumList = EnumUtils.getEnumList(enumClass);
        for (Object en : enumList) {
            Class<?> enClass = en.getClass();
            try {
                Method method = enClass.getMethod(propName); // 需要与枚举类方法对应
                Object invoke = method.invoke(en, null);

                // 获取属性的对应的值
                if (Integer.parseInt(invoke.toString()) == code) {
                    return true;
                }
            } catch (Exception e) {
                log.error("枚举执行getCode方法失败...");
            }
        }
        return false;
    }
}