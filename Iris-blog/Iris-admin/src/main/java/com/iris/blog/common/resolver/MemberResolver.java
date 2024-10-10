package com.iris.blog.common.resolver;

import com.iris.blog.utils.BeanUtil;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * 字段解析
 * @see FieldResolver
 * @see MethodResolver
 */
public interface MemberResolver {

    /**
     * 对传入对象进行处理，包括基本类型、集合、数组。
     *
     * @param obj
     * @throws Exception
     */
    default void resolve(Object obj) throws Exception {
        if (obj == null) {
            return;
        }
        if (obj instanceof Collection) {
            this.resolveCollection((Collection) obj);
            return;
        }
        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            this.resolveArray(obj);
            return;
        }
        if (BeanUtil.isSimpleType(clazz)) {
            return;
        }
        //
        this.doResolve(obj);
    }

    /**
     * 对传入对象进行处理，不包括基本类型、集合、数组。
     *
     * @param obj
     * @throws Exception
     */
    void doResolve(Object obj) throws Exception;

    /**
     * 遍历集合
     *
     * @param collection 集合
     * @throws Exception
     */
    default void resolveCollection(Collection collection) throws Exception {
        for (Object item : collection) {
            this.resolve(item);
        }
    }

    /**
     * 遍历数组
     *
     * @param array 数组
     * @throws Exception
     */
    default void resolveArray(Object array) throws Exception {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            this.resolve(Array.get(array, i));
        }
    }
}
