package com.iris.blog.service;

import com.iris.blog.common.R;
import com.iris.blog.domain.vo.weather.WeatherVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lstar
 * @create 2024-05
 * @description:
 */
public interface CommonService {

    /**
     * 获取天气信息
     * @param cityCode
     * @return
     */
    R<WeatherVO> getWeatherInfo(String cityCode);
}
