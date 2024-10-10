package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 首页统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemHomeGatewayStatisticsVO {

    @ApiModelProperty(value = "接口请求量")
    private Integer urlCount;


    @ApiModelProperty(value = "ip请求量")
    private Integer ipCount;

    @ApiModelProperty(value = "响应数据大小")
    private BigDecimal dataSize;

    @ApiModelProperty(value = "平均响应时长")
    private BigDecimal avgRespTime;
}