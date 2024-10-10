package com.iris.blog.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * @author lstar
 * @create 2021-04
 * @description: lambda常用工具类
 */
@Slf4j
public class LambdaUtil {


    /**
     *  lambda表达式对两个List进行循环,根据符合条件,进行相关的赋值操作并返回这个对象集合
     * @param sourceList   待设置源列表
     * @param srcEqualProp   源对象条件判断属性名
     * @param srcSetProp     源对象待设置属性名
     * @param targetList     资源提供者列表
     * @param tarEqualProp   对象条件判断参数名
     * @param tarGetProp     待获取对象属性名
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T,U>List<T> setListByEqualObjProperty(List<T> sourceList, String srcEqualProp, String srcSetProp,
                                                         List<U> targetList, String tarEqualProp, String tarGetProp){
        List<T> resultList;
        resultList = sourceList.stream()
                .map(sur-> targetList.stream()
                        .filter(tar -> Objects.equals(getValueByPropName(sur, srcEqualProp), getValueByPropName(tar, tarEqualProp)))
                        .findFirst()
                        .map(tar -> {
                            setValueByPropName(sur, srcSetProp, getValueByPropName(tar, tarGetProp));
                            return sur;
                        } ).orElse(null))
                .collect(Collectors.toList());

        return  resultList;
    }

    /**
     *  lambda表达式对两个List进行循环,根据符合条件,进行相关的赋值操作并返回这个对象集合
     * @param sourceList   待设置源列表
     * @param srcEqualProp   源对象条件判断属性名
     * @param srcSetProp     源对象待设置属性名
     * @param targetList     资源提供者列表
     * @param tarEqualProp   对象条件判断参数名
     * @param tarGetProp     待获取对象属性名
     * @param <T>
     * @param <U>
     * @return
     */

    public static <T,U>List<T> setListByEqualObjPropertyReturnSourceList(List<T> sourceList, String srcEqualProp, String srcSetProp,
                                                                         List<U> targetList, String tarEqualProp, String tarGetProp){
        List<T> resultList;
        resultList = sourceList.stream()
                .map(sur-> {
                    targetList.stream()
                            .filter(tar -> Objects.equals(getValueByPropName(sur, srcEqualProp), getValueByPropName(tar, tarEqualProp)))
                            .forEach(o -> {
                                setValueByPropName(sur, srcSetProp, getValueByPropName(o, tarGetProp));
                            });
                    return sur;
                }).collect(Collectors.toList());

        return  resultList;
    }

    /**
     *  通过遍历两个List中按id属性相等的归结到resultList中
     * @param oneList  源list 1
     * @param twoList  源list 2
     * @param equalKeyName 相等的map键值
     */
    public static List<Map<Object, Object>> compareListHitData(List<Map<Object, Object>> oneList, List<Map<Object, Object>> twoList, Object equalKeyName) {
        List<Map<Object, Object>> resultList = oneList.stream().map(map -> twoList.stream()
                .filter(m -> Objects.equals(m.get(equalKeyName),map.get(equalKeyName)))
                .findFirst().map(m -> {
                    map.putAll(m);
                    return map;
                }).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
        return resultList;
    }


    /**
     *   取出两个集合属性值相等的数据
     * @param oneList   源list 1
     * @param twoList   源list 2
     * @param os    相等参数 String id,String name;
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T,U>List<U> compareListHitDataReturnSourceList(List<T> oneList, List<U> twoList,String ...os){
        List<String> list = Arrays.asList(os);
        List<U> resultList;
        resultList  = oneList.stream().map(t -> twoList.stream().filter(s ->{
            boolean flag = false;
            for (String o : list) {
                if (! (Objects.nonNull(getValueByPropName(t, o)) &&
                        Objects.nonNull(getValueByPropName(s, o)) &&
                        Objects.equals(getValueByPropName(t, o),getValueByPropName(s, o)))) {
                    flag = true;
                }
            }
            return !flag;
        }
        ).findAny()
                .orElse(null))
                .filter(Objects::nonNull)
                .collect(toList());

        return  resultList;
    }

    // 通过属性获取传入对象的指定属性的值
    public static <T> T getValueByPropName(Object object, String propName) {
        T value = null;
        try {
            // 通过属性获取对象的属性
            Field field = object.getClass().getDeclaredField(propName);
            // 对象的属性的访问权限设置为可访问
            field.setAccessible(true);
            // 获取属性的对应的值
            value = (T)field.get(object);
        } catch (Exception e) {
            return null;
        }

        return value;
    }

    // 通过属性设置传入对象的指定属性的值
    public static <U> void setValueByPropName(Object object, String propName, U updateValue) {

        try {
            // 通过属性获取对象的属性
            Field field = object.getClass().getDeclaredField(propName);
            // 对象的属性的访问权限设置为可访问
            field.setAccessible(true);
            // 设置属性的对应的值
            field.set(object, updateValue);
        } catch (Exception e) {
            log.error("setValueByPropName.error {}", propName, e);
        }


    }

    /**
     * list分组操作
     *
     * @param list：要操作的list集合
     * @param function:需要进行分组的对象属性
     * @return Map<K, List < V>>
     */
    public static <K, V> Map<K, List<V>> listGroupingBy(List<V> list, Function<V, K> function) {
        return list.stream().collect(Collectors.groupingBy(function));
    }

    /**
     * list分组统计
     *
     * @param function:需要进行分组的对象属性
     * @param list ：要操作的list集合
     * @return Map<K, List < V>>
     */
    public static <K, V> Map<K, Long> listGroupingAndSumInt(List<V> list, Function<V, K> function) {
        return list.stream().collect( Collectors.groupingBy(function, Collectors.counting()));
    }

    /**
     * 分组结果中某个属性的统计
     * @param list  要操作的list集合
     * @param function  需要进行分组的对象属性
     * @param mapper    需要进行统计的对象属性
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, IntSummaryStatistics> listGroupingResultSumInt(List<V> list, Function<V, K> function,ToIntFunction<? super V> mapper) {
        return list.stream().collect( Collectors.groupingBy(function, Collectors.summarizingInt(mapper)));
    }

    /**
     * 分组结果中某个属性的统计
     * @param list  要操作的list集合
     * @param function  需要进行分组的对象属性
     * @param mapper    需要进行统计的对象属性
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, LongSummaryStatistics> listGroupingResultSumLong(List<V> list, Function<V, K> function,ToLongFunction<? super V> mapper) {
        return list.stream().collect( Collectors.groupingBy(function, Collectors.summarizingLong(mapper)));
    }

    /**
     * list根据对象指定属性进行转Map
     *
     * @param list：要操作的list集合
     * @param function:转成Map后的key 注意的是：如果有相同的key,则保留key1,key2,则保留key1舍弃key2
     * @return Map<K, V>
     */
    public static <K, V> Map<K, V> listToMap(List<V> list, Function<V, K> function) {
        return list.stream().collect(Collectors.toMap(function, a -> a, (k1, k2) -> k1));
    }

    /**
     * list根据对象指定属性+条件过滤出新的List
     *
     * @param list：要操作的list集合
     * @param predicate:      过滤条件，例如a->a.getUserName().equals("xxx)
     *                        过滤出名字=xxx的集合
     * @return List<T>
     */
    public static <T> List<T> listFilter(List<T> list, Predicate<? super T> predicate) {

        return list.stream().filter(predicate).collect(toList());
    }

    /**
     * list根据对象指定属性+条件过滤出新的List
     *
     * @param from：要操作的list集合
     * @param predicate:      过滤条件，例如a->a.getUserName().equals("xxx)
     *                        过滤出名字=xxx的集合
     * @return List<T>
     */
    public static <T> List<T> filterList(Collection<T> from, Predicate<T> predicate) {
        if (CollUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(predicate).collect(Collectors.toList());
    }
    /**
     * list根据对象指定属性满足指定条件并返回布尔值(任意匹配到一个)
     *
     * @param list：要操作的list集合
     * @param predicate:      过滤条件，例如a->a.getUserName().equals("xxx)
     *                        过滤出名字=xxx的集合
     * @return List<T>
     */
    public static <T> boolean listAnyMatch(List<T> list, Predicate<? super T> predicate) {
        return list.stream().anyMatch(predicate);
    }

    /**
     * list根据对象指定属性满足指定条件并返回布尔值(没有匹配到一个)
     *
     * @param list：要操作的list集合
     * @param predicate:      过滤条件，例如a->a.getUserName().equals("xxx)
     *                        过滤出名字=xxx的集合
     * @return List<T>
     */
    public static <T> boolean listNoMatch(List<T> list, Predicate<? super T> predicate) {
        return list.stream().noneMatch(predicate);
    }

    /**
     * list根据对象收集出新的list
     *
     * @param list   ：要操作的list集合
     * @param mapper : 收集
     * @return List<T>
     */
    public static <T, R, A> List<R> listToList(List<T> list, Function<? super T, ? extends R> mapper) {
        return list.stream().map(mapper).collect(toList());
    }

    /**
     * list根据对象多个指定属性去重
     *
     * @param list：要操作的list集合
     * @param keyExtractors:   去重属性
     * @return List<T>
     */

    public static <T> List<T> listDistinctByMoreCondition(List<T> list, Function<? super T, ?>... keyExtractors) {
        return list.stream()
                .filter(distinctByMoreKey(keyExtractors))
                .collect(toList());
    }

    /**
     * list根据对象指定属性去重
     *
     * @param list：要操作的list集合
     * @param keyExtractor:   去重属性
     * @return List<T>
     */
    public static <T> List<T> listDistinctBy(List<T> list, Function<? super T, ?> keyExtractor) {
        return list.stream()
                .filter(distinctByKey(keyExtractor))
                .collect(toList());
    }

    //jdk 8集合根据属性去重工具
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    //根据多属性去重工具
    public static <T> Predicate<T> distinctByMoreKey(Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };

    }

    /**
     * list去重（适用于简单包装的类型，如String,Integer..）
     *
     * @param list：要操作的list集合
     * @return List<T>
     */
    public static <T> List<T> listDistinct(List<T> list) {
        return list.stream().distinct().collect(toList());
    }

    /**
     * 对比老、新两个列表，找出新增、修改、删除的数据
     *
     * @param oldList  老列表
     * @param newList  新列表
     * @param sameFunc 对比函数，返回 true 表示相同，返回 false 表示不同
     *                 注意，same 是通过每个元素的“标识”，判断它们是不是同一个数据
     * @return [新增列表、修改列表、删除列表]
     */
    public static <T> List<List<T>> diffList(Collection<T> oldList, Collection<T> newList,
                                             BiFunction<T, T, Boolean> sameFunc) {
        List<T> createList = new LinkedList<>(newList); // 默认都认为是新增的，后续会进行移除
        List<T> updateList = new ArrayList<>();
        List<T> deleteList = new ArrayList<>();

        // 通过以 oldList 为主遍历，找出 updateList 和 deleteList
        for (T oldObj : oldList) {
            // 1. 寻找是否有匹配的
            T foundObj = null;
            for (Iterator<T> iterator = createList.iterator(); iterator.hasNext(); ) {
                T newObj = iterator.next();
                // 1.1 不匹配，则直接跳过
                if (!sameFunc.apply(oldObj, newObj)) {
                    continue;
                }
                // 1.2 匹配，则移除，并结束寻找
                iterator.remove();
                foundObj = newObj;
                break;
            }
            // 2. 匹配添加到 updateList；不匹配则添加到 deleteList 中
            if (foundObj != null) {
                updateList.add(foundObj);
            } else {
                deleteList.add(oldObj);
            }
        }
        return asList(createList, updateList, deleteList);
    }

    /**
     * list求和 (int求和)
     *
     * @param list：要操作的list集合
     * @return List<T>
     */
    public static <T> int listToSumIntBy(List<T> list, ToIntFunction<? super T> mapper) {
        return list.stream().mapToInt(mapper).sum();
    }
    /**
     * list求和 (long求和)
     *
     * @param list：要操作的list集合
     * @return List<T>
     */
    public static <T> long listToSumLongBy(List<T> list, ToLongFunction<? super T> mapper) {
        return list.stream().mapToLong(mapper).sum();
    }

    /**
     * list求和 (double求和)
     *
     * @param list：要操作的list集合
     * @return List<T>
     */
    public static <T> double listToSumDoubleBy(List<T> list, ToDoubleFunction<? super T> mapper) {
        return list.stream().mapToDouble(mapper).sum();
    }

    /**
     * list求和 (int求平均值)
     *
     * @param list：要操作的list集合(size不能为0,否则返回0)
     * @return List<T>
     */
    public static <T> double listToIntAvgBy(List<T> list, ToIntFunction<? super T> mapper) {
        boolean present = list.stream().mapToInt(mapper).average().isPresent();
        double asDouble = 0.0;
        if (present) {
            asDouble = list.stream().mapToInt(mapper).average().getAsDouble();
        }
        return asDouble;
    }

    /**
     * list求和 (int求最大值)
     *
     * @param list：要操作的list集合(size不能为0,否则返回0)
     * @return List<T>
     */
    public static <T> int listToMaxIntBy(List<T> list, ToIntFunction<? super T> mapper) {
        boolean present = list.stream().mapToInt(mapper).max().isPresent();
        int asInt = 0;
        if (present) {
            asInt = list.stream().mapToInt(mapper).max().getAsInt();
            return asInt;
        }
        return 0;
    }

    /**
     * list求和 (int求最小值)
     *
     * @param list：要操作的list集合(size不能为0,否则返回0)
     * @return List<T>
     */
    public static <T> int listToMinIntBy(List<T> list, ToIntFunction<? super T> mapper) {
        boolean present = list.stream().mapToInt(mapper).min().isPresent();
        int asInt = 0;
        if (present) {
            asInt = list.stream().mapToInt(mapper).min().getAsInt();
            return asInt;
        }
        return 0;
    }

    /**
     * list转换String 用逗号分割
     *
     * @param list：要操作的list集合(size不能为0,否则返回0)
     * @return List<T>
     */
    public static <T, R, A> String listToStringComma(List<T> list, Function<? super T, ? extends R> mapper) {
        return StringUtils.join(list.stream().map(mapper).collect(toList()), ",");
    }

    /**
     * String转换list 用逗号分割
     *
     * @param data：操作数据 如：A=1,2,3  A= A,B,A
     * @return List<T>
     */
    public static <T> List<String> StringTolistComma(String data) {
        return Arrays.stream(StringUtils.split(data,",")).map(String::trim).collect(toList());
    }


    /**
     * double 类型的集合求和
     *
     * @param doubles
     * @return
     */
    public static Double doubleSum(List<Double> doubles) {
        return doubles.stream().collect(Collectors.summarizingDouble(value -> value)).getSum();
    }


    /**
     * 两个字符串List 取交集
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<String> intersectionListString(List<String> listOne, List<String> listTwo) {
        return listOne.stream().filter(item -> listTwo.contains(item)).collect(toList());
    }

    /**
     * 两个字符串List 差集 (list1 - list2)
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<String> subtractionListString(List<String> listOne, List<String> listTwo) {
        return listOne.stream().filter(item -> !listTwo.contains(item)).collect(toList());
    }
    /**
     * 两个字符串List 差集 (list2 - list1)
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<String> subtractionListStrings(List<String> listOne, List<String> listTwo) {
        return listTwo.stream().filter(item -> !listOne.contains(item)).collect(toList());
    }

    /**
     * List 并集
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<String> unionListString(List<String> listOne, List<String> listTwo) {
        List<String> listAll = listOne.parallelStream().collect(toList());
        List<String> listAll2 = listTwo.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        return listAll;
    }


    /**
     * 两个整形List 取交集
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<Integer> intersectionListInt(List<Integer> listOne, List<Integer> listTwo) {
        return listOne.stream().filter(item -> listTwo.contains(item)).collect(toList());
    }

    /**
     * List int 并集
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<Integer> unionListInt(List<Integer> listOne, List<Integer> listTwo) {
        List<Integer> listAll = listOne.parallelStream().collect(toList());
        List<Integer> listAll2 = listTwo.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        //List->Set
        Set<Integer> sortSet = new LinkedHashSet<>(listAll);
        //Set->List
        return new ArrayList<>(sortSet);
    }


    /**
     * 截取字符串,可以替代list.subList[start,end)
     *
     * @param list       要截取的List
     * @param startIndex 开始位置
     * @param endIndex   结束位置
     * @param <T>        返回类型
     * @return 截取后结果
     */
    public static <T> List<T> subList(List<T> list, int startIndex, int endIndex) {
        return list.stream()
                .skip(startIndex)
                .limit(endIndex - startIndex)
                .collect(toList());
    }


    /** 相同属性的会复制过去
     * 从List<A> copy到List<B>
     * @param list List<B>
     * @param clazz B
     * @return List<B>
     */
    public static <T> List<T> listCopyToList(List<?> list,Class<T> clazz){
        String oldOb = JSON.toJSONString(list);
        return JSON.parseArray(oldOb, clazz);
    }

    /**
     * 从对象A copy到 对象B
     * @param ob A
     * @param clazz B.class
     * @return B
     */
    public static <T> T objectCopyObj(Object ob,Class<T> clazz){
        String oldOb = JSON.toJSONString(ob);
        return JSON.parseObject(oldOb, clazz);
    }

    /***
     * List ---> set
     * @param from
     * @return
     * @param <T>
     */
    public static <T> Set<T> convertSet(Collection<T> list) {
        return convertSet(list, v -> v);
    }
    /**
     * List ---> set
     * @param list
     * @param func
     * @return
     * @param <T>
     * @param <U>
     */
    public static <T, U> Set<U> convertSet(Collection<T> list, Function<T, U> func) {
        if (CollUtil.isEmpty(list)) {
            return new HashSet<>();
        }
        return list.stream().map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * List ---> set
     * @param list
     * @param func
     * @param filter
     * @return
     * @param <T>
     * @param <U>
     *
     *     例如:convertSet(list, UserVO::getInfo,
     *                 UserInfoVO::getApproved);
     */
    public static <T, U> Set<U> convertSet(Collection<T> list, Function<T, U> func, Predicate<T> filter) {
        if (CollUtil.isEmpty(list)) {
            return new HashSet<>();
        }
        return list.stream().filter(filter).map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 集合转换不等于空出新集合
     * @param list
     * @param func
     * @return
     * @param <T>
     * @param <U>
     */

    public static <T, U> List<U> convertList(Collection<T> list, Function<T, U> func) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 集合转换
     * @param list
     * @param func
     * @return
     * @param <T>
     * @param <U>
     *     例如: convertList(list, UserDO::getId)
     */

    public static <T, U> List<U> convertList(T[] list, Function<T, U> func) {
        if (ArrayUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return convertList(Arrays.asList(list), func);
    }


    /**
     * 集合转换 map
     * @param list
     * @param func
     * @param filter
     * @return
     * @param <T>
     * @param <U>
     *
     * 例如:convertList(scopes.entrySet(), Map.Entry::getKey, Map.Entry::getValue);
     */
    public static <T, U> List<U> convertList(Collection<T> list, Function<T, U> func, Predicate<T> filter) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().filter(filter).map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 判断是否包含
     * @param source
     * @param targets
     * @return
     *
     * 例如:containsAny(status, StatusEnum.NORMAL.getStatus(), StatusEnum.STOP.getStatus())
     */
    public static boolean containsAny(Object source, Object... targets) {
        return asList(targets).contains(source);
    }

    /**
     * 判断集合是否任意一个空的
     * @param collections
     * @return
     */
    public static boolean isAnyEmpty(Collection<?>... collections) {
        return Arrays.stream(collections).anyMatch(CollectionUtil::isEmpty);
    }

    /**
     * 判断集合是否任意一个满足条件
     * @param list
     * @param predicate i -> i.getStatus() == 1
     * @return
     * @param <T>
     */
    public static <T> boolean anyMatch(Collection<T> list, Predicate<T> predicate) {
        return list.stream().anyMatch(predicate);
    }

    /**
     *返回流中的第一个元素，如果流为空，则返回一个空的Optional对象。
     * @param list
     * @return
     * @param <T>
     */
    public static <T> T getFirst(List<T> list) {
        return !CollectionUtil.isEmpty(list) ? list.get(0) : null;
    }

    /**
     * 返回流中的第一个元素，如果流为空，则返回一个空的Optional对象。
     * @param list
     * @param predicate
     * @return
     * @param <T>
     */
    public static <T> T findFirst(Collection<T> list, Predicate<T> predicate) {
        return findFirst(list, predicate, Function.identity());
    }

    /**
     * 返回流中的第一个元素，如果流为空，则返回一个空的Optional对象。
     * @param list
     * @param predicate
     * @param func
     * @return
     * @param <T>
     * @param <U>
     */
    public static <T, U> U findFirst(Collection<T> list, Predicate<T> predicate, Function<T, U> func) {
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().filter(predicate).findFirst().map(func).orElse(null);
    }
    /**
     * 嵌套集合扁平化返回新的集合
     * @param list
     * @param func
     * @return
     * @param <T>
     * @param <U>
     */
    public static <T, U> List<U> convertListByFlatMap(Collection<T> list,
                                                      Function<T, ? extends Stream<? extends U>> func) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().filter(Objects::nonNull).flatMap(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 嵌套集合扁平化返回新的集合
     * @param list
     * @param mapper
     * @param func
     * @return
     * @param <T>
     * @param <U>
     * @param <R>
     */
    public static <T, U, R> List<R> convertListByFlatMap(Collection<T> list,
                                                         Function<? super T, ? extends U> mapper,
                                                         Function<U, ? extends Stream<? extends R>> func) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(mapper).filter(Objects::nonNull).flatMap(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 合并map的value
     * @param map
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K, V> List<V> mergeValuesFromMap(Map<K, List<V>> map) {
        return map.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    //测试
    public static void main(String[] args) {

        // 准备参数
        Collection<Dog> oldList = Arrays.asList(
                new Dog(1, "青青", "qq"),
                new Dog(2, "璐璐", "ll")
        );
        Collection<Dog> newList = Arrays.asList(
                new Dog(null, "青青", "qq2"),
                new Dog(null, "小白", "xb")
        );
        BiFunction<Dog, Dog, Boolean> sameFunc = (oldObj, newObj) -> {
            boolean same = oldObj.getCode().equals(newObj.getCode());
            // 如果相等的情况下，需要设置下 id，后续好更新
            if (same) {
                newObj.setId(oldObj.getId());
            }
            return same;
        };

        // 调用
        List<List<Dog>> result = diffList(oldList, newList, sameFunc);
    }

    @Data
    @AllArgsConstructor
    private static class Dog {

        private Integer id;
        private String name;
        private String code;

    }
    //开始--------------------------------BigDecimal常见计算,不建议使用double和float参与计算,存在精度问题--------------------------

    //内部使用函数式接口
    @FunctionalInterface
    public interface ToBigDecimalFunction<T> {
        BigDecimal applyAsBigDecimal(T value);
    }

    static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner,
                      Function<A, R> finisher, Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    //求和方法
    private static <T> Collector<T, ?, BigDecimal> summingBigDecimal(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{BigDecimal.ZERO},
                (a, t) -> {
                    a[0] = a[0].add(mapper.applyAsBigDecimal(t));
                },
                (a, b) -> {
                    a[0] = a[0].add(b[0]);
                    return a;
                },
                a -> a[0], CH_NOID);
    }

    //求最大值
    private static <T> Collector<T, ?, BigDecimal> maxBy(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(Long.MIN_VALUE)},
                (a, t) -> {
                    a[0] = a[0].max(mapper.applyAsBigDecimal(t));
                },
                (a, b) -> {
                    a[0] = a[0].max(b[0]);
                    return a;
                },
                a -> a[0], CH_NOID);
    }

    //求最小值
    private static <T> Collector<T, ?, BigDecimal> minBy(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(Long.MAX_VALUE)},
                (a, t) -> {
                    a[0] = a[0].min(mapper.applyAsBigDecimal(t));
                },
                (a, b) -> {
                    a[0] = a[0].min(b[0]);
                    return a;
                },
                a -> a[0], CH_NOID);
    }

    //求平均值
    private static <T> Collector<T, ?, BigDecimal> averagingBigDecimal(ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO},
                (a, t) -> {
                    a[0] = a[0].add(mapper.applyAsBigDecimal(t));
                    a[1] = a[1].add(BigDecimal.ONE);
                },
                (a, b) -> {
                    a[0] = a[0].add(b[0]);
                    return a;
                },
                a -> a[0].divide(a[1], BigDecimal.ROUND_HALF_UP).setScale(newScale, roundingMode), CH_NOID);
    }

    /**
     * list去重 (BigDecimal求和)
     *
     * @param list：要操作的list集合
     * @param newScale:       保留小数位
     * @return List<T>
     */
    public static <T> BigDecimal listToSumBigDecimalBy(List<T> list, ToBigDecimalFunction<? super T> mapper, int newScale) {
        return list.stream().collect(LambdaUtil.summingBigDecimal(mapper)).setScale(newScale);
    }

    /**
     * list去重 (BigDecimal求平均值)
     *
     * @param list：要操作的list集合
     * @param newScale:       保留小数位
     * @param roundingMode    : 舍弃规则
     * @return List<T>
     */
    public static <T> BigDecimal listToAveragingBigDecimalBy(List<T> list, ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return list.stream().collect(LambdaUtil.averagingBigDecimal(mapper, newScale, roundingMode));
    }

    /**
     * list去重 (BigDecimal求大值)
     *
     * @param list：要操作的list集合
     * @param newScale:       保留小数位
     * @param roundingMode    : 舍弃规则
     * @return List<T>
     */
    public static <T> BigDecimal listToMaxBigDecimalBy(List<T> list, ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return list.stream().collect(LambdaUtil.maxBy(mapper)).setScale(newScale, roundingMode);
    }

    /**
     * list去重 (BigDecimal求最小值)
     *
     * @param list：要操作的list集合
     * @param newScale:       保留小数位
     * @param roundingMode    : 舍弃规则
     * @return List<T>
     */
    public static <T> BigDecimal listToMinBigDecimalBy(List<T> list, ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return list.stream().collect(LambdaUtil.maxBy(mapper)).setScale(newScale, roundingMode);
    }

    //结束-------------------------------------------------------
}

