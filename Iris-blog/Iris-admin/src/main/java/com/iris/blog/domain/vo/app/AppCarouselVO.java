package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @create 2024-08
 * @description: 背景轮播图
 */

@Data
@ApiModel(value = "背景轮播图")
public class AppCarouselVO {


    /**
     * 轮播图id
     */
    @ApiModelProperty(value = "轮播图id")
    private Integer id;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;
}
