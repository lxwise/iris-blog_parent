package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 友情链接
 */
@Data
@ApiModel(value="友情链接")
public class SearchFriendLinkDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "网站名称")
	private String name;

	@ApiModelProperty(value = "是否展示:0申请,1展示,2不展示")
	private Integer status;

}
