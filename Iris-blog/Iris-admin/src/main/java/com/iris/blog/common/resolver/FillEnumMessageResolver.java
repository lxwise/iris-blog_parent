package com.iris.blog.common.resolver;

import com.iris.blog.common.annotation.FillEnumMessage;
import com.iris.blog.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * 填充枚举信息解析器
 */
@Component
@Slf4j
public class FillEnumMessageResolver implements FieldResolver, MethodResolver {


    /**
     * 遍历传入对象的属性（Field）和方法（Method），
     * <p>
     * 如果持有 {@link FillEnumMessage @FillEnumMessage} 注解，则根据 {@link FillEnumMessage#enumFieldName()} 指定的字段获取实体类字段枚举值，
     * <p>
     * 再根据 {@link FillEnumMessage#fileName()} 指定的字段获取枚举字段对应的值
     * <p>
     * 最后填充给持有 {@link FillEnumMessage @FillEnumMessage} 注解的属性和方法。
     * 如果持有 {@link FillEnumMessage @FillEnumMessage} 注解的属性不是String类型，则递归遍历该属性引用的对象。
     * <p>
     * 如果持有 {@link FillEnumMessage @FillEnumMessage} 注解的属性不为空，则不填充。
     * <p>
     * 持有 {@link FillEnumMessage @FillEnumMessage} 注解的方法只能有一个 String 类型的参数。
     *
     * @param obj
     */
    public void fill(Object obj) {
        try {
            this.resolve(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doResolve(Object obj) throws Exception {
        this.resolveFields(obj);
        this.resolveMethods(obj);
    }

    @Override
    public void resolveMethod(Object obj, Method method) throws IllegalAccessException, InvocationTargetException {
        Class<?> clazz = obj.getClass();
        // 方法是否持有 @FillEnumMessage 注解
        FillEnumMessage fillEnumMessageAnnotation = method.getAnnotation(FillEnumMessage.class);
        if (fillEnumMessageAnnotation == null) {
            return;
        }
        // 方法只能有一个 String 类型的参数
        if (method.getParameterCount() != 1 || method.getParameterTypes()[0] != String.class) {
            log.error("持有@FillEnumMessage注解的方法[{}#{}]只能有一个String类型的参数",
                    clazz.getName(), method.getName());
            return;
        }
        // 根据 @FillEnumMessage 注解指定的 enum 字段，获取枚举的信息
        String enumMessage = this.readEnumMessage(obj, fillEnumMessageAnnotation, method);
        if (enumMessage == null) {
            return;
        }
        BeanUtil.invokeMethod(obj, method, enumMessage);
    }

    @Override
    public void resolveField(Object obj, Field field) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        // 字段是否持有 @FillEnumMessage 注解
        FillEnumMessage fillEnumMessageAnnotation = field.getAnnotation(FillEnumMessage.class);
        if (fillEnumMessageAnnotation == null) {
            return;
        }
        // 获取持有 @FillEnumMessage 注解的字段的值
        Object fieldValue = BeanUtil.readField(obj, field,
                () -> log.warn("持有@FillEnumMessage注解的字段[{}#{}]缺少Getter方法", clazz.getName(), field.getName()));
        // 如果持有 @FillEnumMessage 注解的字段不是 String 类型，则递归遍历该字段引用的对象。
        if (field.getType() != String.class) {
            this.fill(fieldValue);
            return;
        }
        // field 已经有值则不填充
        if (fieldValue != null) {
            return;
        }
        // 根据 @FillEnumMessage 注解指定的 enum 字段，获取枚举实例对应的信息
        String enumMessage = this.readEnumMessage(obj, fillEnumMessageAnnotation, field);
        if (enumMessage == null) {
            return;
        }
        // 给持有 @FillEnumMessage 注解的字段赋值
        BeanUtil.writeField(obj, field, enumMessage,
                () -> log.warn("持有@FillEnumMessage注解的字段[{}#{}]缺少Setter方法", clazz.getName(), field.getName()));
    }

    /**
     * 如果持有 {@link FillEnumMessage @FillEnumMessage} 注解，则根据 {@link FillEnumMessage#enumFieldName()} 指定的字段获取实体类字段枚举值，
     * <p>
     * 再根据 {@link FillEnumMessage#fileName()} 指定的字段获取枚举字段对应的值
     *
     * @param obj
     * @param fillEnumMessage {@link FillEnumMessage @FillEnumMessage} 注解对象
     * @param member          持有 {@link FillEnumMessage @FillEnumMessage} 注解的 Method 或 Field 对象
     * @return 枚举实例对应的信息
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private String readEnumMessage(Object obj, FillEnumMessage fillEnumMessage, Member member)
            throws IllegalAccessException, InvocationTargetException {
        Object enumValue = this.readEnumValue(obj, fillEnumMessage, member);
        if (!(enumValue instanceof Enum)) {
            return null;
        }
        Enum<?> instance = (Enum<?>) enumValue;
        try {
            if (StringUtils.isNotBlank(fillEnumMessage.fileName())) {
                Field tagField = instance.getClass().getDeclaredField(fillEnumMessage.fileName());
                tagField.setAccessible(true);
                Object tagValue = tagField.get(instance);
                return tagValue != null ? tagValue.toString() : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 从 {@link FillEnumMessage @FillEnumMessage} 注解指定的 enum 字段读取枚举值
     *
     * @param obj
     * @param fillEnumMessage {@link FillEnumMessage @FillEnumMessage} 注解对象
     * @param member          持有 {@link FillEnumMessage @FillEnumMessage} 注解的 Method 或 Field 对象
     * @return 枚举实例
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object readEnumValue(Object obj, FillEnumMessage fillEnumMessage, Member member)
            throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        // 获取 @FillEnumMessage 注解指定的 enum 字段名
        String enumFieldName = fillEnumMessage.enumFieldName();
        if (StringUtils.isBlank(enumFieldName)) {
            log.error("[{}#{}]持有的@FillEnumMessage注解没有指定enumFieldName", clazz.getName(), member.getName());
            return null;
        }
        // 获取 @FillEnumMessage 注解指定的 enum 字段类型
        Class<? extends Enum> enumFieldType = fillEnumMessage.enumFieldType();
        try {
            // 通过 enum 字段的 Getter 方法读取枚举值
            Method readMethod = BeanUtil.getReadMethod(clazz, enumFieldName, enumFieldType);
            if (!enumFieldType.isAssignableFrom(readMethod.getReturnType())) {
                log.error("[{}#{}]持有的@FillEnumMessage注解指定的类型[{}]与字段[{}]的Getter方法[{}]的返回类型[{}]不匹配",
                        clazz.getName(), member.getName(), enumFieldType.getSimpleName(),
                        enumFieldName, readMethod.getName(), readMethod.getReturnType().getSimpleName());
                return null;
            }
            return BeanUtil.invokeMethod(obj, readMethod);
        } catch (NoSuchMethodException e) {
            log.warn("[{}#{}]持有的@FillEnumMessage注解指定的字段[{}]缺少Getter方法",
                    clazz.getName(), member.getName(), enumFieldName);
            // 没有 Getter 方法时，直接操作 enum 字段的 Field 对象
            Field enumField = BeanUtil.getDeclaredField(clazz, enumFieldName);
            if (enumField == null) {
                log.error("[{}#{}]持有的@FillEnumMessage注解指定的字段[{}]不存在",
                        clazz.getName(), member.getName(), enumFieldName);
                return null;
            }
            if (!enumFieldType.isAssignableFrom(enumField.getType())) {
                log.error("[{}#{}]持有的@FillEnumMessage注解指定的类型[{}]与字段[{}]的类型[{}]不匹配",
                        clazz.getName(), member.getName(), enumFieldType.getSimpleName(),
                        enumFieldName, enumField.getType().getSimpleName());
                return null;
            }
            return BeanUtil.readField(obj, enumField);
        }
    }

}
