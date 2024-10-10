package com.iris.blog.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.common.PageBean;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页工具
 */
@SuppressWarnings("unchecked")
public class PageUtil {


    /**
     * mybatis-plus 分页适配
     *
     * @param page page
     * @param list list
     * @return com.pzds.framework.core.domain.PageBean<T>
     **/
    public static <T> PageBean<T> pageBean(Page page, List<T> list) {
        return new PageBean(Long.valueOf(page.getCurrent()).intValue(), Long.valueOf(page.getPages()).intValue(), page.getTotal(), list);
    }


    /**
     * mybatis-plus 分页适配
     *
     * @param page page
     * @return com.pzds.framework.core.domain.PageBean<T>
     **/
    public static <T> PageBean<T> pageBean(Page<T> page) {
        return new PageBean(Long.valueOf(page.getCurrent()).intValue(), Long.valueOf(page.getPages()).intValue(), page.getTotal(), page.getRecords());
    }


    /**
     * mybatis-plus 分页适配
     *
     * @param page  page
     * @param clazz clazz
     * @return com.pzds.framework.core.domain.PageBean<T>
     **/
    public static <T> PageBean<T> pageBean(Page<?> page, Class<T> clazz) {
        List<T> collect = page.getRecords().stream().map(o -> BeanUtil.newBean(o, clazz)).collect(Collectors.toList());
        return pageBean(page, collect);
    }
}