package com.iris.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.iris.blog.common.R;
import com.iris.blog.common.constant.CommonConstant;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.domain.vo.weather.CityInfoVO;
import com.iris.blog.domain.vo.weather.ForecastVO;
import com.iris.blog.domain.vo.weather.WeatherInfoVO;
import com.iris.blog.domain.vo.weather.WeatherVO;
import com.iris.blog.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author lstar
 * @create 2024-05
 * @description:
 */
@Service
@Slf4j
public class CommonServiceImpl implements CommonService {


    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public R<WeatherVO> getWeatherInfo(String cityCode) {
        // 使用默认城市代码，如果传入的城市代码为空或无效
        if (StringUtils.isBlank(cityCode)) {
            cityCode = CommonConstant.WEATHER_CODE;
        }

        // 尝试从Redis中获取天气信息
        WeatherVO keyValue = redisUtil.getKeyValue(RedisKeyConstant.SYS_WEATHER_INFO_KEY + cityCode, WeatherVO.class);
        if (ObjectUtil.isNotNull(keyValue)) {
            return R.ok(keyValue); // 如果Redis中有缓存的数据，则直接返回
        }

        // 构造请求URL
        String url = String.format(CommonConstant.WEATHER_URL, cityCode);
        try {
            // 发送HTTP请求获取天气数据
            ResponseEntity<WeatherVO> response = restTemplate.getForEntity(url, WeatherVO.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BusinessException("获取天气数据失败，HTTP状态码：" + response.getStatusCodeValue());
            }
            // 解析响应体
            WeatherVO resp = response.getBody();
            if (resp == null || resp.getData() == null || CollectionUtils.isEmpty(resp.getData().getForecast())) {
                throw new BusinessException("获取天气数据失败，响应体为空或格式错误");
            }
            // 处理并存储数据
            WeatherInfoVO data = resp.getData();
            data.setForecastDay(data.getForecast().get(0));
            data.setForecast(null); // 清除不再需要的预报列表
            // 缓存天气信息到Redis，有效期为24小时
            redisUtil.saveRedisValue(RedisKeyConstant.SYS_WEATHER_INFO_KEY + cityCode, resp, 60 * 60 * 24L);

            return R.ok(resp); // 返回天气信息
        } catch (Exception e) {
            throw new BusinessException("获取天气数据失败", e);
        }
    }

}
