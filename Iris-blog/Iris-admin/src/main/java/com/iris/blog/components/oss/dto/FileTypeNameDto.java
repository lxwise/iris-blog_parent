package com.iris.blog.components.oss.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author l-xin
 * @create 2023-10
 * @description:
 */
@Data
public class FileTypeNameDto {

    @ApiModelProperty(value = "目录名:yaml中配置")
    private String directoryName;

}
