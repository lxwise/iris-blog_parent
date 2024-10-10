package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-08
 * @description: 首页轮播
 */
@Data
@ApiModel(value="首页轮播")
public class CarouselVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "轮播图地址")
	private String imgUrl;

	@ApiModelProperty(value = "是否显示 (0否 1是)")
	private Boolean status;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

}
