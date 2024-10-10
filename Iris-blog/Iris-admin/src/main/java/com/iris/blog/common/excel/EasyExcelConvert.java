package com.iris.blog.common.excel;
 
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.util.StringUtils;
 
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lstar
 * @create 2023-04
 * @description: excel导入导出针对枚举类型的转换器
 */
public class EasyExcelConvert implements Converter<Object> {
    /**
     * 枚举列表
     */
    private Map<String, String> enumMap = new HashMap<>();

    /**
     * excel转化后的类型
     *
     * @return
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }
 
    /**
     * excel中的数据类型,统一设置字符串
     *
     * @return
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }
 
    /**
     * 导入转换
     * @param cellData            当前单元格对象
     * @param contentProperty     当前单元格属性
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String cellMsg = cellData.getStringValue();
        Field field = contentProperty.getField();
        EnumFiledConvert enumFiledConvert = field.getAnnotation(EnumFiledConvert.class);
        if (enumFiledConvert == null) {
            return null;
        }
        String enumStr = enumFiledConvert.enumMap();
        // 解析枚举映射关系
        getEnumMap(enumStr, true);
        // 是否为单选
        boolean single = enumFiledConvert.single();
        // 如果是单选，默认Java属性为integer
        if (single) {
            String res = enumMap.get(cellMsg);
            return StringUtils.hasText(res) ? Integer.valueOf(res) : null;
        } else {
            // 多选分隔符
            String spiteChar = enumFiledConvert.spiteChar();
            // 多选枚举，默认Java属性为字符串，格式为 key1,key2,key3
            List<String> strStr = Arrays.asList(cellMsg.split(spiteChar)).stream().map(s -> String.valueOf(enumMap.get(s))).collect(Collectors.toList());
            String str = String.join(spiteChar, strStr);
            return str;
        }
    }
 
    /**
     * 导出转化
     * @param value               当前值
     * @param contentProperty     当前单元格属性
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Field field = contentProperty.getField();
        EnumFiledConvert enumFiledConvert = field.getAnnotation(EnumFiledConvert.class);
        if (enumFiledConvert == null) {
            return new WriteCellData<>();
        }
        // 解析枚举字符串
        String enumStr = enumFiledConvert.enumMap();
        getEnumMap(enumStr, false);
        // 是否为单选
        boolean single = enumFiledConvert.single();
        // 如果是单选，默认Java属性为integer
        if (single) {
            return new WriteCellData(enumMap.getOrDefault(String.valueOf(value), ""));
        } else {
            // 多选分隔符
            String spiteChar = enumFiledConvert.spiteChar();
            // 多选枚举，默认Java属性为字符串，格式为 key1,key2,key3
            List<String> strStr = Arrays.asList(String.valueOf(value).split(spiteChar)).stream().map(s -> String.valueOf(enumMap.get(s))).collect(Collectors.toList());
            String str = String.join(spiteChar, strStr);
            return new WriteCellData(str);
        }
    }
 
    /**
     * 根据注解配置的枚举映射字符串进行解析到map中
     * @param mapStr
     * @param readOrWrite 读excel 、 写excel
     */
    private void getEnumMap(String mapStr, boolean readOrWrite) {
        String[] enumS = mapStr.split(",");
        for (String anEnum : enumS) {
            String[] data = anEnum.split("-");
            if (readOrWrite) {
                // 读excel excel中的数据都是value，转换成key
                enumMap.put(data[1], data[0]);
            } else {
                // 写excel  Java中的数据都是key，转换成value
                enumMap.put(data[0], data[1]);
            }
        }
    }
}