package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 标签选项VO
 */
@Data
@ApiModel(description = "标签选项VO")
public class AppTagOptionVO {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Integer id;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名")
    private String tagName;
}