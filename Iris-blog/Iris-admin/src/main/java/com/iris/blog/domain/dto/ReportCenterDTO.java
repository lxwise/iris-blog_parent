package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 报表下载中心
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ReportCenter对象", description="报表下载中心")
public class ReportCenterDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "报表名称")
    private String name;

    @ApiModelProperty(value = "操作人名字")
    private String operatorName;


}
