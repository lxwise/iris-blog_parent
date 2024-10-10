package com.iris.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.dao.mapper.GeneratorMapper;
import com.iris.blog.domain.dto.PageGeneratorDTO;
import com.iris.blog.utils.GenUtils;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.common.R;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

 /**
 * @author lstar
 * @create 2023-04
 * @description: 代码生成器实现类
 */
@Service
public class SysGeneratorService {
    @Autowired
    private GeneratorMapper generatorDao;


    public R queryList(PageGeneratorDTO dto) {
        Page page = new Page<>(dto.getPage(), dto.getPageSize());
        List<Map<String, Object>> maps = generatorDao.queryList(page, dto.getTableName());
        page.setRecords(maps);
        PageBean<List<Map<String, Object>>> listPageBean = PageUtil.pageBean(page);
        return R.ok(listPageBean);
    }

    public Map<String, String> queryTable(String tableName) {
        return generatorDao.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorDao.queryColumns(tableName);
    }


    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }

        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
