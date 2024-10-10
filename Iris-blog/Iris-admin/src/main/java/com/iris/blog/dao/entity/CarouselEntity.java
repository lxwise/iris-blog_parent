package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_carousel")
@ApiModel(value="首页轮播")
public class CarouselEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键")
	private Integer id;
	/**
	 * 轮播图地址
	 */
	
	@TableField(value = "img_url")
	@ApiModelProperty(value = "轮播图地址")
	private String imgUrl;
	/**
	 * 是否显示 (0否 1是)
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "是否显示 (0否 1是)")
	private Boolean status;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;
}
