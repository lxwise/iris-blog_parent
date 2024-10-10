package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 首页统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemHomeContributeStatisticsVO {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String date;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer count;
}