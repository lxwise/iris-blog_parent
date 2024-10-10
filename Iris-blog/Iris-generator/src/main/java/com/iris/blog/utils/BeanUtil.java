package com.iris.blog.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Locale.ENGLISH;

/**
 * Bean工具类
 */
@Slf4j
public class BeanUtil {

    public static final String GET_PREFIX = "get";
    public static final String SET_PREFIX = "set";
    public static final String IS_PREFIX = "is";

    private BeanUtil() {
    }

    /**
     * 创建目标对象实例，并把源对象实例的属性值拷贝给目标对象实例。
     *
     * @param source      源对象的实例
     * @param targetClass 目标对象的类型
     * @return T 目标对象的实例
     */
    public static <T> T newBean(Object source, Class<T> targetClass) {
        try {
            T target = BeanUtils.instantiateClass(targetClass);
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把源对象实例的属性值拷贝给目标对象实例。
     * 会拷贝为null的属性值，但是会拷贝为""的属性
     *
     * @param source 源对象的实例
     * @param target 目标对象的实例
     */
    public static void copyProperties(Object source, Object target) {
        try {
            copyPropertiesReWrite(source, target, null, (String[]) null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T toBean(Object source, Class<T> targetClass) {
        return cn.hutool.core.bean.BeanUtil.toBean(source, targetClass);
    }

    public static <T> T toBean(Object source, Class<T> targetClass, Consumer<T> peek) {
        T target = toBean(source, targetClass);
        if (target != null) {
            peek.accept(target);
        }
        return target;
    }

    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        return convertList(source, s -> toBean(s, targetType));
    }

    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType, Consumer<T> peek) {
        List<T> list = toBean(source, targetType);
        if (list != null) {
            list.forEach(peek);
        }
        return list;
    }
    /**
     * 重写
     * 把源对象实例的属性值拷贝给目标对象实例。
     * 不会拷贝为null的属性值，但是会拷贝为""的属性
     *
     * @param source 源对象的实例
     * @param target 目标对象的实例
     */
    public static void copyPropertiesIncludeNull(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void copyPropertiesReWrite(Object source, Object target,  Class<?> editable,
                                              String... ignoreProperties) throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if (value == null) {
                                continue;
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    public static void copyProperties(Object source, Object target, Class<?> editable) {
        try {
            BeanUtils.copyProperties(source, target, editable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        try {
            BeanUtils.copyProperties(source, target, ignoreProperties);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target){
        if(sourceList == null){
            return null;
        }

        List targetList = new ArrayList<>(sourceList.size());
        try {
            for(Object source : sourceList){
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return targetList;
    }
    /**
     * 检查给定类型是否表示“简单”类型：primitive，CharSequence，Number，Date，URI，URL，Locale，Class，
     * 以及相应的数组。
     *
     * @param clazz 类的 Class 对象
     * @return
     */
    public static boolean isSimpleType(Class<?> clazz) {
        return BeanUtils.isSimpleProperty(clazz);
    }

    /**
     * 获取类的属性（Field）对象，包括 public，protected，default (package) access，private 属性，包括父类的属性。
     *
     * @param clazz 类的 Class 对象
     * @return 属性对象列表
     */
    public static List<Field> getDeclaredFields(Class<?> clazz) {
        return getDeclaredFields(clazz, Object.class);
    }

    /**
     * 获取类的属性（Field）对象，包括 public，protected，default (package) access，private 属性，包括父类的属性。
     *
     * @param clazz 类的 Class 对象
     * @param stop  指定递归结束的 Class
     * @return 属性对象列表
     */
    public static List<Field> getDeclaredFields(Class<?> clazz, Class stop) {
        List<Field> fieldList = new ArrayList<>();
        getDeclaredFields(clazz, stop, fieldList);
        return fieldList;
    }

    /**
     * 递归获取父类的属性（Field）对象
     *
     * @param clazz     类的 Class 对象
     * @param stop      指定递归结束的 Class
     * @param fieldList 属性对象列表
     */
    private static void getDeclaredFields(Class<?> clazz, Class stop, List<Field> fieldList) {
        Field[] fields = clazz.getDeclaredFields();
        fieldList.addAll(Arrays.asList(fields));
        Class superclass = clazz.getSuperclass();
        if (superclass == stop) {
            return;
        }
        getDeclaredFields(superclass, stop, fieldList);
    }

    /**
     * 根据属性名获取类的属性（Field）对象，当前类中不存在时从父类获取。
     *
     * @param clazz     类的 Class 对象
     * @param fieldName 属性名
     * @return 属性对象不存在时返回 null
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        return getDeclaredField(clazz, Object.class, fieldName);
    }

    /**
     * 根据属性名获取类的属性（Field）对象，当前类中不存在时从父类获取。
     *
     * @param clazz     类的 Class 对象
     * @param stop      指定递归结束的 Class
     * @param fieldName 属性名
     * @return 属性对象不存在时返回 null
     */
    public static Field getDeclaredField(Class<?> clazz, Class stop, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superclass = clazz.getSuperclass();
            if (superclass == stop) {
                return null;
            }
            // 递归从父类获取
            return getDeclaredField(superclass, stop, fieldName);
        }
    }

    /**
     * 获取类的属性（Field）的值
     *
     * @param obj   类的 Class 对象
     * @param field 属性（Field）对象
     * @return 属性的值
     * @throws IllegalAccessException
     */
    public static Object readField(Object obj, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * 获取类的属性（Field）的值。
     * <p>
     * 先通过 Getter 方法获取属性值，没有 Getter 方法时，通过 Field 对象获取。
     *
     * @param obj        类的 Class 对象
     * @param field      属性（Field）对象
     * @return 属性的值
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object readField(Object obj, Field field, Runnable action)
            throws InvocationTargetException, IllegalAccessException {
        try {
            // 从该字段的 Getter 方法读取字段值
            Method readMethod = BeanUtil.getReadMethod(obj.getClass(), field);
            return BeanUtil.invokeMethod(obj, readMethod);
        } catch (NoSuchMethodException e) {
            if(null != action){
                action.run();
            }
            // 没有 Getter 方法时，直接操作 Field 对象
            return BeanUtil.readField(obj, field);
        }
    }

    /**
     * 设置类的属性（Field）的值
     *
     * @param obj   类的 Class 对象
     * @param field 属性（Field）对象
     * @param value 要设置的新值
     * @throws IllegalAccessException
     */
    public static void writeField(Object obj, Field field, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * 设置类的属性（Field）的值。
     * <p>
     * 先通过 Setter 方法设置属性值，没有 Setter 方法时，通过 Field 对象设置。
     *
     * @param obj        类的 Class 对象
     * @param field      属性（Field）对象
     * @param value      要设置的新值
     * @param action 没有 Setter 方法时的日志处理
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void writeField(Object obj, Field field, Object value, Runnable action)
            throws InvocationTargetException, IllegalAccessException {
        try {
            // 通过字段的 Setter 方法设置值
            Method writeMethod = BeanUtil.getWriteMethod(obj.getClass(), field);
            BeanUtil.invokeMethod(obj, writeMethod, value);
        } catch (NoSuchMethodException e) {
            if(null != action){
                action.run();
            }
            // 没有 Setter 方法时，直接操作 Field 对象
            BeanUtil.writeField(obj, field, value);
        }
    }


    /**
     * 设置类的属性（Field）的值。
     * <p>
     * 先通过 Setter 方法设置属性值，没有 Setter 方法时，通过 Field 对象设置。
     *
     * @param obj        类的 Class 对象
     * @param field      属性（Field）对象
     * @param value      要设置的新值
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void writeFieldWithSetter(Object obj, Field field, Object value)
            throws InvocationTargetException, IllegalAccessException {
        try {
            // 通过字段的 Setter 方法设置值
            Method writeMethod = BeanUtil.getWriteMethod(obj.getClass(), field);
            BeanUtil.invokeMethod(obj, writeMethod, value);
        } catch (NoSuchMethodException e) {
            // 没有 Setter 方法时，直接操作 Field 对象
            BeanUtil.writeField(obj, field, value);
        }
    }

    /**
     * 将字符串的第一个字母大写
     *
     * @param name 原字符串
     * @return 首字母大写后的字符串
     */
    public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

    /**
     * 获取类的方法（Method）对象，包括 public，protected，default (package) access，private 方法，包括父类的方法。
     *
     * @param clazz 类的 Class 对象
     * @return 方法对象列表
     */
    public static List<Method> getDeclaredMethods(Class<?> clazz) {
        return getDeclaredMethods(clazz, Object.class);
    }

    /**
     * 获取类的方法（Method）对象，包括 public，protected，default (package) access，private 方法，包括父类的方法。
     *
     * @param clazz 类的 Class 对象
     * @param stop  指定递归结束的 Class
     * @return 方法对象列表
     */
    public static List<Method> getDeclaredMethods(Class<?> clazz, Class stop) {
        List<Method> methodList = new ArrayList<>();
        getDeclaredMethods(clazz, stop, methodList);
        return methodList;
    }

    /**
     * 递归获取父类的方法（Method）对象
     *
     * @param clazz      类的 Class 对象
     * @param stop       指定递归结束的 Class
     * @param methodList 方法对象列表
     */
    private static void getDeclaredMethods(Class<?> clazz, Class stop, List<Method> methodList) {
        Method[] methods = clazz.getDeclaredMethods();
        methodList.addAll(Arrays.asList(methods));
        Class superclass = clazz.getSuperclass();
        if (superclass == stop) {
            return;
        }
        getDeclaredMethods(superclass, stop, methodList);
    }

    /**
     * 根据方法名获取类的方法（Method）对象，当前类中不存在时从父类获取。
     *
     * @param clazz          类的 Class 对象
     * @param methodName     方法名
     * @param parameterTypes 方法的参数类型数组
     * @return 方法对象不存在时返回 null
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        return getDeclaredMethod(clazz, Object.class, methodName, parameterTypes);
    }

    /**
     * 根据方法名获取类的方法（Method）对象，当前类中不存在时从父类获取。
     *
     * @param clazz          类的 Class 对象
     * @param stop           指定递归结束的 Class
     * @param methodName     方法名
     * @param parameterTypes 方法的参数类型数组
     * @return 方法对象不存在时返回 null
     */
    public static Method getDeclaredMethod(Class<?> clazz, Class stop, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class superclass = clazz.getSuperclass();
            if (superclass == stop) {
                return null;
            }
            // 递归从父类获取
            return getDeclaredMethod(superclass, stop, methodName, parameterTypes);
        }
    }

    /**
     * 获取类的属性对应的 Getter 方法
     *
     * @param clazz 类的 Class 对象
     * @param field 属性对象
     * @return 属性对应的 Getter 方法对象
     * @throws NoSuchMethodException
     */
    public static Method getReadMethod(Class<?> clazz, Field field) throws NoSuchMethodException {
        return getReadMethod(clazz, field.getName(), field.getType());
    }

    /**
     * 获取类的属性对应的 Getter 方法
     * <p>
     * 属性类型为 boolean.class 时，Getter 方法为 isXXX()，否则为 getXXX()。
     *
     * @param clazz     类的 Class 对象
     * @param fieldName 属性名
     * @param fieldType 属性类型
     * @return 属性对应的 Getter 方法对象
     * @throws NoSuchMethodException
     */
    public static Method getReadMethod(Class<?> clazz, String fieldName, Class<?> fieldType)
            throws NoSuchMethodException {
        if (fieldType == boolean.class) {
            return clazz.getMethod(IS_PREFIX + capitalize(fieldName));
        }
        return clazz.getMethod(GET_PREFIX + capitalize(fieldName));
    }

    /**
     * 获取类的属性对应的 Setter 方法
     *
     * @param clazz 类的 Class 对象
     * @param field 属性对象
     * @return 属性对应的 Setter 方法对象
     * @throws NoSuchMethodException
     */
    public static Method getWriteMethod(Class<?> clazz, Field field) throws NoSuchMethodException {
        return getWriteMethod(clazz, field.getName(), field.getType());
    }

    /**
     * 获取类的属性对应的 Setter 方法
     *
     * @param clazz     类的 Class 对象
     * @param fieldName 属性名
     * @param fieldType 属性类型
     * @return 属性对应的 Setter 方法对象
     * @throws NoSuchMethodException
     */
    public static Method getWriteMethod(Class<?> clazz, String fieldName, Class<?> fieldType)
            throws NoSuchMethodException {
        return clazz.getMethod(SET_PREFIX + capitalize(fieldName), fieldType);
    }

    /**
     * 调用类的方法
     *
     * @param obj    类的 Class 对象
     * @param method 方法（Method）对象
     * @param args   方法的参数数组
     * @return 方法的返回值
     */
    public static Object invokeMethod(Object obj, Method method, Object... args)
            throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
