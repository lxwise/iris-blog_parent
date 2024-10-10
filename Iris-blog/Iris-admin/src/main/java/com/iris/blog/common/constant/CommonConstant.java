package com.iris.blog.common.constant;

/**
 * @author lstar
 * @create 2023-04
 * @description: 公共常量
 */
public interface CommonConstant {

    /**
     * AES加密算法KEY
     */
    String KEY = "IrisAdminAuthorisdashuaige666!!!";
    /**
     * 默认登录密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * Session登录用户
     */
    String LOGIN_USER = "login-user";

    /**
     * 未知
     */
    String UNKNOWN = "UNKNOWN";

    /**
     * 菜单编号 - 根节点
     */
    Long ID_ROOT = 0L;

    /**
     * 天气信息
     */
    String WEATHER_URL = "http://t.weather.itboy.net/api/weather/city/%s";
    /**
     * 北京天气城市编码
     */
    String WEATHER_CODE = "101010100";

    /**
     * 腾讯地图api
     */
    String TENCENT_MAP_URL = "https://apis.map.qq.com/ws/location/v1/ip?ip=%s&key=%s";

    /**
     * 每秒请求量已达到上限
     */
    Integer MAP_STATUS_CODE = 120;
    /**
     * 每日调用量已达到上限
     */
    Integer MAP_STATUS_CODE_DAY = 121;

    /**
     * 高亮标签
     */
    String PRE_TAG = "<span style='color:#f47466'>";

    /**
     * 高亮标签
     */
    String POST_TAG = "</span>";
}
