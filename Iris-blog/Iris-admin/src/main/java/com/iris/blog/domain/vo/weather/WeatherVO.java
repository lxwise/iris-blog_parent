package com.iris.blog.domain.vo.weather;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.iris.blog.domain.vo.weather.CityInfoVO;
import com.iris.blog.domain.vo.weather.WeatherInfoVO;
/**
 * @author lstar
 * @create 2024-05
 * @description:
 */
@ApiModel(value = "天气信息")
@Data
public class WeatherVO {

    @ApiModelProperty(value = "响应信息")
    private String message;

    @ApiModelProperty(value = "响应状态码")
    private int status;

    @ApiModelProperty(value = "日期",example = "20240522")
    private String date;

    @ApiModelProperty(value = "时间",example = "2024-05-22 09:11:19")
    private String time;

    @ApiModelProperty(value = "城市信息")
    private CityInfoVO cityInfo;

    @ApiModelProperty(value = "天气详细信息")
    private WeatherInfoVO data;
}


