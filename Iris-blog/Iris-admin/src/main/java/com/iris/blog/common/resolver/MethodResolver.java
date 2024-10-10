package com.iris.blog.common.resolver;

import com.iris.blog.utils.BeanUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 方法解析
 */
public interface MethodResolver extends MemberResolver {

    /**
     * 遍历传入对象的方法（Method）
     *
     * @param obj
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    default void resolveMethods(Object obj)
            throws IllegalAccessException, InvocationTargetException {
        List<Method> methods = BeanUtil.getDeclaredMethods(obj.getClass());
        for (Method method : methods) {
            this.resolveMethod(obj, method);
        }
    }

    /**
     * 对传入对象的 Method 进行处理
     *
     * @param obj
     * @param method
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    void resolveMethod(Object obj, Method method)
            throws IllegalAccessException, InvocationTargetException;
}
