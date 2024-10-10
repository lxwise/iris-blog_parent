package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 友情链接表
 */
@Data
@ApiModel(value="友情链接表")
public class FriendLinkDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "网站名称")
	private String name;

	@ApiModelProperty(value = "网站地址")
	private String url;

	@ApiModelProperty(value = "网站logo")
	private String image;

	@ApiModelProperty(value = "网站描述")
	private String info;

	@ApiModelProperty(value = "联系邮箱")
	private String email;

	@ApiModelProperty(value = "是否展示:0申请,1展示,2不展示")
	private Integer status;

	@ApiModelProperty(value = "排序")
	private Integer sort;

}
