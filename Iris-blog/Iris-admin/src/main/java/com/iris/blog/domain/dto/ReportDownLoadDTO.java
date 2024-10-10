package com.iris.blog.domain.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 下载信息
 */
@Data
public class ReportDownLoadDTO {

    @ApiModelProperty(value = "文件名")
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    @ApiModelProperty(value = "文件下载地址 例如:/goods/analyse/list/excel")
    @NotBlank(message = "文件下载地址不能为空")
    private String url;

    @ApiModelProperty(value = "文件下载后缀")
    @NotBlank(message = "文件下载后缀不能为空")
    private String suffix;

    @ApiModelProperty(value = "服务名 例如:iris-admin")
    @NotBlank(message = "服务名称不能为空")
    private String serviceName;

    @ApiModelProperty(value = "userId 前端忽略",hidden = true)
    private Long userId;

    @ApiModelProperty(value = "userName 前端忽略",hidden = true)
    private String userName;

    @ApiModelProperty(value = "请求参数")
    private JSONObject params;

}
