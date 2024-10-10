package com.iris.blog.domain.vo.weather;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @create 2024-09
 * @description:
 */
@ApiModel(value = "天气城市信息")
@Data
public class CityInfoVO {

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "城市编码")
    private String citykey;

    @ApiModelProperty(value = "省份")
    private String parent;

    @ApiModelProperty(value = "更新时间", example = "08:10")
    private String updateTime;
}
