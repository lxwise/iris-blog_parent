package com.iris.blog.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * @author lstar
 * @create 2023-04
 * @description: 数据库接口
 */
public interface GeneratorMapper {
    /**
     * 查询表分页数据
     * @param page
     * @param tableName
     * @return
     */
    List<Map<String, Object>> queryList(Page page, @Param("tableName") String tableName);

    /**
     * 根据表名查询表信息
     * @param tableName
     * @return
     */
    Map<String, String> queryTable(String tableName);

    /**
     * 根据表名查询列信息
     * @param tableName
     * @return
     */
    List<Map<String, String>> queryColumns(String tableName);
}
