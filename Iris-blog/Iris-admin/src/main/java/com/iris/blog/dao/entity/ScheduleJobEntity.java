package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度表
 */
@Data
@TableName("t_schedule_job")
@ApiModel(value="定时任务调度表")
public class ScheduleJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "任务ID")
	private Long id;
	/**
	 * 任务名称
	 */
	
	@TableField(value = "job_name",condition= SqlCondition.LIKE)
	@ApiModelProperty(value = "任务名称")
	private String jobName;
	/**
	 * 任务组名
	 */
	
	@TableField(value = "job_group",condition= SqlCondition.LIKE)
	@ApiModelProperty(value = "任务组名")
	private String jobGroup;
	/**
	 * 调用目标字符串
	 */
	
	@TableField(value = "invoke_target")
	@ApiModelProperty(value = "调用目标字符串")
	private String invokeTarget;
	/**
	 * cron执行表达式
	 */
	
	@TableField(value = "cron_expression")
	@ApiModelProperty(value = "cron执行表达式")
	private String cronExpression;
	/**
	 * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
	 */
	
	@TableField(value = "misfire_policy")
	@ApiModelProperty(value = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
	private Integer misfirePolicy;

	/**
	 * 状态（0正常 1暂停）
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "状态（0暂停 1正常）")
	private Integer status;
	/**
	 * 创建者
	 */
	
	@TableField(value = "create_by")
	@ApiModelProperty(value = "创建者")
	private String createBy;
	/**
	 * 更新者
	 */
	
	@TableField(value = "update_by")
	@ApiModelProperty(value = "更新者")
	private String updateBy;
	/**
	 * 备注信息
	 */
	
	@TableField(value = "remark")
	@ApiModelProperty(value = "备注信息")
	private String remark;
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
