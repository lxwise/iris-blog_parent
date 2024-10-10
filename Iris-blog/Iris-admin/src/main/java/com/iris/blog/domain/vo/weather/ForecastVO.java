package com.iris.blog.domain.vo.weather;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @create 2024-09
 * @description:
 */
@ApiModel(value = "天气预报详细信息")
@Data
public class ForecastVO {

    @ApiModelProperty(value = "时间", example = "22")
    private String date;

    @ApiModelProperty(value = "最高温度", example = "高温 32℃")
    private String high;

    @ApiModelProperty(value = "最低温度", example = "低温 23℃")
    private String low;

    @ApiModelProperty(value = "年月日", example = "2024-05-22")
    private String ymd;

    @ApiModelProperty(value = "星期几", example = "星期五")
    private String week;

    @ApiModelProperty(value = "日出时间", example = "06:06")
    private String sunrise;

    @ApiModelProperty(value = "日落时间", example = "18:06")
    private String sunset;

    @ApiModelProperty(value = "空气质量指数", example = "65")
    private Integer aqi;

    @ApiModelProperty(value = "风向", example = "西北风")
    private String fx;

    @ApiModelProperty(value = "风力等级", example = "3级")
    private String fl;

    @ApiModelProperty(value = "天气类型", example = "晴")
    private String type;

    @ApiModelProperty(value = "通知", example = "天气寒冷，注意穿衣")
    private String notice;
}
