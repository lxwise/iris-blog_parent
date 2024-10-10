package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 报表下载中心
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ReportCenter对象VO", description="报表下载中心VO")
public class ReportCenterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "报表名称")
    private String name;

    @ApiModelProperty(value = "报表时间")
    private Date time;

    @ApiModelProperty(value = "状态(0:处理中，1：已完成, 2:下载失败)")
    private Integer status;
    private String statusName;

    @ApiModelProperty(value = "报表地址")
//    @ResourceUrl(value = "#jiayu.yoga.config.oldResourceUrl", multi = DataType.NONE)
    private String ossAddress;

    @ApiModelProperty(value = "下载结果")
    private String result;

    @ApiModelProperty(value = "操作人")
    private Integer operatorId;

    @ApiModelProperty(value = "操作人中文")
    private String operatorName;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
