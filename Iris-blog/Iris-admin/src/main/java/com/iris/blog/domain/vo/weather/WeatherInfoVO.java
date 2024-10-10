package com.iris.blog.domain.vo.weather;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.iris.blog.domain.vo.weather.ForecastVO;
import java.util.List;

/**
 * @author lstar
 * @create 2024-09
 * @description:
 */
@ApiModel(value = "天气详细信息")
@Data
public class WeatherInfoVO {

    @ApiModelProperty(value = "湿度", example = "98%")
    private String shidu;

    @ApiModelProperty(value = "pm2.5")
    private Integer pm25;

    @ApiModelProperty(value = "pm10")
    private Integer pm10;

    @ApiModelProperty(value = "空气质量", example = "优")
    private String quality;

    @ApiModelProperty(value = "温度", example = "23.8")
    private String wendu;

    @ApiModelProperty(value = "感冒提醒（指数）", example = "洗车")
    private String ganmao;

    @ApiModelProperty(value = "未来15天天气预报集合")
    private List<ForecastVO> forecast;

    @ApiModelProperty(value = "未来15天天气预报集合-一天")
    private ForecastVO forecastDay;
}
