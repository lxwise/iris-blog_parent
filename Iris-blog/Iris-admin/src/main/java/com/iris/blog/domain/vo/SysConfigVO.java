package com.iris.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.localdate.LocalDateStringConverter;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.iris.blog.common.excel.EasyExcelConvert;
import com.iris.blog.common.excel.EnumFiledConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 参数管理
 */
@Data
@ApiModel(value="参数管理")
public class SysConfigVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "id")
	@ExcelProperty("配置编号")
	private Long id;

	@ApiModelProperty(value = "参数编码")
	@ExcelProperty("配置编码")
	private String paramCode;

	@ApiModelProperty(value = "参数值")
	@ExcelProperty("参数值")
	private String paramValue;

	@ApiModelProperty(value = "类型   0：系统参数   1：非系统参数")
	@ExcelProperty(value = "配置类型",converter = EasyExcelConvert.class)
	@EnumFiledConvert(enumMap = "false-系统参数,true-非系统参数")
	private Boolean paramType;

	@ApiModelProperty(value = "备注")
	@ExcelProperty("备注")
	private String remark;

	@ApiModelProperty(value = "状态  0：不使用    1：使用")
	@ExcelIgnore
	private Boolean status;

	@ApiModelProperty(value = "创建时间")
	@ExcelProperty(value = "创建时间",converter = LocalDateTimeStringConverter.class)
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	@ExcelProperty(value = "更新时间",converter = LocalDateTimeStringConverter.class)
	private LocalDateTime updateTime;

}
