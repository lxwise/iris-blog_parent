package com.iris.blog.components.oss.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author l-xin
 * @create 2023-10
 * @description: 上传信息
 */
@Data
@ApiModel(value = "上传信息")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UploadDTO {
    @ApiModelProperty(value = "文件URL")
    private String url;
    @ApiModelProperty(value = "文件大小，单位字节")
    private Long size;

}
