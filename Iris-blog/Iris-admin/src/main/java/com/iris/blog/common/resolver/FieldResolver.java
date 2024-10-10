package com.iris.blog.common.resolver;

import com.iris.blog.utils.BeanUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 字段解析
 */

public interface FieldResolver extends MemberResolver {

    /**
     * 遍历传入对象的属性（Field）
     *
     * @param obj
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @see #resolveField(Object, Field)
     */
    default void resolveFields(Object obj) throws InvocationTargetException, IllegalAccessException {
        List<Field> fields = BeanUtil.getDeclaredFields(obj.getClass());
        for (Field field : fields) {
            this.resolveField(obj, field);
        }
    }

    /**
     * 对传入对象的 Field 进行处理
     *
     * @param obj
     * @param field
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    void resolveField(Object obj, Field field) throws InvocationTargetException, IllegalAccessException;

}
