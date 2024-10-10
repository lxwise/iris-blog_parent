package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 报表下载中心
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_report_center")
@ApiModel(value="ReportCenter对象", description="报表下载中心")
public class ReportCenterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "报表名称")
    @TableField(value = "name",condition = SqlCondition.LIKE)
    private String name;

    @ApiModelProperty(value = "服务名称")
    @TableField("service_name")
    private String serviceName;

    @ApiModelProperty(value = "状态(0:处理中，1：已完成 -1失败)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "处理时长")
    @TableField("duration")
    private Long duration;

    @ApiModelProperty(value = "报表地址")
    @TableField("oss_address")
    private String ossAddress;

    @ApiModelProperty(value = "文件大小 bytes")
    @TableField("file_size")
    private Integer fileSize;

    @TableField(exist = false)
    private BigDecimal fileSizeName;

    @ApiModelProperty(value = "url地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "参数信息")
    @TableField("params")
    private String params;

    @ApiModelProperty(value = "文件后缀")
    @TableField("suffix")
    private String suffix;

    @ApiModelProperty(value = "下载结果")
    @TableField("result")
    private String result;

    @ApiModelProperty(value = "操作人")
    @TableField("operator_id")
    private Long operatorId;

    @ApiModelProperty(value = "操作人名字")
    @TableField(value = "operator_name",condition = SqlCondition.LIKE)
    private String operatorName;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

}
