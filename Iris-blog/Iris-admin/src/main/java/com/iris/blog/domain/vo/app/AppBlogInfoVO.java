package com.iris.blog.domain.vo.app;

import com.iris.blog.domain.vo.SiteConfigVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 博客信息
 */
@Data
@ApiModel(value="博客信息")
public class AppBlogInfoVO extends SiteConfigVO {

	@ApiModelProperty(value = "文章数量")
	private Long articleCount;

	@ApiModelProperty(value = "分类数量")
	private Long categoryCount;

	@ApiModelProperty(value = "标签数量")
	private Long tagCount;

	@ApiModelProperty(value = "网站访问量")
	private String viewCount;

}
