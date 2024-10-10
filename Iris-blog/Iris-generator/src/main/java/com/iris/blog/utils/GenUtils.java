package com.iris.blog.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.iris.blog.common.CodegenColumnHtmlTypeEnum;
import com.iris.blog.dao.entity.ColumnEntity;
import com.iris.blog.dao.entity.TableEntity;
import com.iris.blog.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lstar
 * @create 2023-04
 * @description: 代码生成器 工具类
 */
@Slf4j
public class GenUtils {
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Mapper.xml.vm");
        templates.add("template/Vo.java.vm");
        templates.add("template/Dto.java.vm");

        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/Mapper.java.vm");


        templates.add("template/Api.js.vm");
        templates.add("template/Index.vue.vm");
        templates.add("template/Form.vue.vm");
        return templates;
    }

    private static final Map<String, CodegenColumnHtmlTypeEnum> COLUMN_HTML_TYPE_MAPPINGS =
            MapUtil.<String, CodegenColumnHtmlTypeEnum>builder()
                    .put("status", CodegenColumnHtmlTypeEnum.RADIO)
                    .put("sex", CodegenColumnHtmlTypeEnum.RADIO)
                    .put("type", CodegenColumnHtmlTypeEnum.SELECT)
                    .put("image", CodegenColumnHtmlTypeEnum.IMAGE_UPLOAD)
                    .put("file", CodegenColumnHtmlTypeEnum.FILE_UPLOAD)
                    .put("content", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("description", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("remark", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("text", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("time", CodegenColumnHtmlTypeEnum.DATETIME)
                    .put("date", CodegenColumnHtmlTypeEnum.DATETIME)
                    .build();

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        boolean hasList = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getStringArray("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), columnToJava(columnEntity.getDataType()));
            columnEntity.setAttrType(attrType);


            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            if (!hasList && "array".equals(columnEntity.getExtra())) {
                hasList = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置前端组件类型
        for (ColumnEntity column : tableEntity.getColumns()) {
            // 基于后缀进行匹配
            COLUMN_HTML_TYPE_MAPPINGS.entrySet().stream()
                    .filter(entry -> StrUtil.endWithIgnoreCase(column.getAttrname(), entry.getKey()))
                    .findFirst().ifPresent(entry -> column.setHtmlType(entry.getValue().getType()));

            // 如果是 Boolean 类型时，设置为 radio 类型.
            if (Boolean.class.getSimpleName().equals(column.getDataType())) {
                column.setHtmlType(CodegenColumnHtmlTypeEnum.RADIO.getType());
            }
            // 如果是 LocalDateTime 类型，则设置为 datetime 类型
            if (LocalDateTime.class.getSimpleName().equals(column.getDataType())) {
                column.setHtmlType(CodegenColumnHtmlTypeEnum.DATETIME.getType());
            }
            if (column.getColumnName().contains("s")) {

            }
            // 兜底，设置默认为 input 类型
            if (column.getHtmlType() == null) {
                column.setHtmlType(CodegenColumnHtmlTypeEnum.INPUT.getType());
            }
        }


        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        String mainPath = config.getString("mainPath");
        mainPath = StringUtils.isBlank(mainPath) ? "com.iris" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("hasList", hasList);
        map.put("mainPath", mainPath);
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(LocalDateTime.now(), DateUtils.DATE_PATTERN1));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                String filePath = getFileName(template, tableEntity.getClassName(), config.getString("package"), config);
                log.info("生成文件路径：" + filePath);
                boolean flag = config.getBoolean("useZip");
                if (flag) {
                    //添加到zip
                    zip.putNextEntry(new ZipEntry(filePath));
                    IOUtils.write(sw.toString(), zip, "UTF-8");
                    IOUtils.closeQuietly(sw);
                    zip.closeEntry();
                } else {
                    FileUtils.writeStringToFile(new File(filePath), sw.toString(), "UTF-8");
                }

            } catch (Exception e) {
                throw new BusinessException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String[] tablePrefixArray) {
        if (null != tablePrefixArray && tablePrefixArray.length > 0) {
            for (String tablePrefix : tablePrefixArray) {
                tableName = tableName.replace(tablePrefix, "");
            }
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new BusinessException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, Configuration config) {
        String outputDir = config.getString("outputDir");
        boolean outputCurrDir = config.getBoolean("outputCurrDir");
        String packagePath;
        if (outputCurrDir) {
            packagePath = "/main" + File.separator + "java" + File.separator;
        } else {
            packagePath = "main" + File.separator + "java" + File.separator;
        }
        if (StringUtils.isNotBlank(outputDir)) {
            packagePath = outputDir + packagePath;
        }

        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "dao" + File.separator + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "dao" + File.separator + "mapper" + File.separator + className + "Mapper.java";
        }
        if (template.contains("Dto.java.vm")) {
            return packagePath + "domain" + File.separator + "dto" + File.separator + className + "DTO.java";
        }
        if (template.contains("Vo.java.vm")) {
            return packagePath + "domain" + File.separator + "vo" + File.separator + className + "VO.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            if (StringUtils.isNotBlank(outputDir)) {
                return outputDir + "\\main" + File.separator + "resources" + File.separator + "src" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
            } else {
                return "\\main" + File.separator + "resources" + File.separator + "src" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
            }
        }


        if (template.contains("Api.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + className + "Index.ts";
        }
        if (template.contains("Index.vue.vm")) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + className + "Index.vue";
        }

        if (template.contains("Form.vue.vm")) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + className + "Form.vue";
        }

        return null;
    }

    private static String splitInnerName(String name) {
        name = name.replaceAll("\\.", "_");
        return name;
    }
}
