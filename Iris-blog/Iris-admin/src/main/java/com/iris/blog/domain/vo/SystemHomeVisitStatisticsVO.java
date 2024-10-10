package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 首页统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemHomeVisitStatisticsVO {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String date;

    /**
     * pv
     */
    @ApiModelProperty(value = "pv")
    private Integer pv;

    /**
     * uv
     */
    @ApiModelProperty(value = "uv")
    private Integer uv;
}