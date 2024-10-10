package com.iris.blog.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.iris.blog.common.constant.CommonConstant;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.domain.vo.IPAddressVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.iris.blog.domain.vo.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua_parser.Client;
import ua_parser.Parser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;

/**
 * IP 工具类
 * <p>
 * IP 数据源来自 ip2region.xdb 精简版，基于 <a href="https://gitee.com/zhijiantianya/ip2region"/> 项目
 */
@Component
@Slf4j
public class IPUtils {

    private static final String UNKNOWN = "未知";

    private final static String LOCAL_IP = "127.0.0.1";
    private final static String LOCAL_IP_V6 = "0:0:0:0:0:0:0:1";

    private static String tencentMapKey;

    private static RestTemplate restTemplate;

    private static StringRedisTemplate redisTemplate;
    @Autowired
    public IPUtils(@Value("${tencent_map_key}") String tencentMapKey) {
        IPUtils.tencentMapKey = tencentMapKey;
    }
    @Resource
    public void setRestTemplate(RestTemplate restTemplate) {
        IPUtils.restTemplate = restTemplate;
    }
    @Resource
    public void setStringRedisTemplate(StringRedisTemplate redisTemplate) {
        IPUtils.redisTemplate = redisTemplate;
    }

    /**
     * 初始化 SEARCHER
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    private final static IPUtils INSTANCE = new IPUtils();

    /**
     * IP 查询器，启动加载到内存中
     */
    private static Searcher SEARCHER;

    /**
     * 私有化构造
     */
    private IPUtils() {
        try {
            long now = System.currentTimeMillis();
            byte[] bytes = ResourceUtil.readBytes("ip2region.xdb");
            SEARCHER = Searcher.newWithBuffer(bytes);
            log.info("启动加载 IPUtils 成功，耗时 ({}) 毫秒", System.currentTimeMillis() - now);
        } catch (IOException e) {
            log.error("启动加载 IPUtils 失败", e);
        }
    }

    /**
     * 查询 IP 对应的地区编号
     *
     * @param ip IP 地址，格式为 127.0.0.1
     * @return 地区id
     */
    @SneakyThrows
    public static Integer getAreaId(String ip) {
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = LOCAL_IP;
        }
        return Integer.parseInt(SEARCHER.search(ip.trim()));
    }

    /**
     * 查询 IP 对应的地区编号
     *
     * @param ip IP 地址的时间戳，格式参考{@link Searcher#checkIP(String)} 的返回
     * @return 地区编号
     */
    @SneakyThrows
    public static Integer getAreaId(long ip) {
        return Integer.parseInt(SEARCHER.search(ip));
    }

    /**
     * 查询 IP 对应的地区
     *
     * @param ip IP 地址，格式为 127.0.0.1
     * @return 地区
     */
    public static Area getArea(String ip) {
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = LOCAL_IP;
        }
        return AreaUtils.getArea(getAreaId(ip));
    }

    /**
     * 查询 IP 对应的地区
     *
     * @param ip IP 地址的时间戳，格式参考{@link Searcher#checkIP(String)} 的返回
     * @return 地区
     */
    public static Area getArea(long ip) {
        return AreaUtils.getArea(getAreaId(ip));
    }


    /**
     * 通过用户代理获取设备信息
     *
     * @return 设备信息详细信息
     */
    public static String getDeviceDetails() {
        String deviceDetails = UNKNOWN;
        try {
            HttpServletRequest request = getRequest();
            if(null == request){
                return deviceDetails;
            }
            String userAgent = request.getHeader("user-agent");
            Parser parser = new Parser();

            Client client = parser.parse(userAgent);
            if (nonNull(client)) {
                deviceDetails = client.userAgent.family + " " + client.userAgent.major +
                        "-" + client.os.family + " " + client.os.major;
            }

            return deviceDetails;
        } catch (IOException e) {
            log.error("获取用户代理设备信息失败");
        }
        return deviceDetails;
    }

    /**
     * 通过用户代理获取设备信息
     *
     * @param userAgent 用户代理 HTTP 设备
     * @return 设备信息详细信息
     * @throws IOException if not found
     */
    private static String getDeviceDetails(String userAgent) throws IOException {
        String deviceDetails = UNKNOWN;

        Parser parser = new Parser();

        Client client = parser.parse(userAgent);
        if (nonNull(client)) {
            deviceDetails = client.userAgent.family + " " + client.userAgent.major +
                    "-" + client.os.family + " " + client.os.major;
        }

        return deviceDetails;
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {

        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return requestAttributes.getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取本地IP地址
     *
     * @return 本地IP地址
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return LOCAL_IP;
    }

    /**
     * 获取本地主机名
     *
     * @return
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return UNKNOWN;
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getIp() {
        HttpServletRequest request = getRequest();
        String ipAddress;
        try {
            assert request != null;
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                //HTTP_CLIENT_IP：有些代理服务器
                ipAddress = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            }

            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                //X-Real-IP：nginx服务代理
                ipAddress = request.getHeader("X-Real-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCAL_IP.equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                if (LOCAL_IP_V6.equals(ipAddress)){
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = LOCAL_IP;
        }
        return ipAddress;
    }


    /**
     * 获取ip地址
     *
     * @param ip
     * @return
     */
    public static String getIpAreaAddress(String ip) {

        if (StringUtils.isBlank(ip)) {
            ip = getIp();
        }
        Area area = IPUtils.getArea(ip);
        if (area == null) {
            return UNKNOWN;
        }
        return AreaUtils.format(area.getId());
    }

    /**
     * 获取远程主机id
     * @return
     */
    public static String getRemoteId() {
        String ip = getIp();
        String ua = getDeviceDetails();
        if (StrUtil.isNotBlank(ip)) {
            return ip + ua;
        }
        return Objects.requireNonNull(getRequest()).getRemoteAddr() + ua;
    }

    /**
     * 获取ip地址
     *
     * @param ip
     * @return
     */
    public static IPAddressVO getIpAddress(String ip) {
        IPAddressVO vo = new IPAddressVO();
        if (StringUtils.isBlank(ip)) {
            ip = getIp();
        }
        //ip缓存
        String ipStr = ip.replace(".", "").replace(":", "");
        String ipKey = RedisKeyConstant.SYS_IP_KEY + ipStr;
        Object value = redisTemplate.opsForValue().get(ipKey);
        if(Objects.nonNull(value)){
            vo = JSONObject.parseObject(value.toString(), IPAddressVO.class);
            return vo;
        }

        vo.setIp(ip);
        vo.setDevice(getDeviceDetails());

        // 获得城市
        Area area = IPUtils.getArea(ip);
        String areaAddress = area != null ? AreaUtils.format(area.getId()) : null;

        if (areaAddress == null || StringUtils.isBlank(areaAddress)) {
            setUnknownLocation(vo);
        } else {
            String[] split = areaAddress.split("-");
            vo.setCountry(split[0]);
            if (split.length > 1) {
                vo.setProvince(split[1]);
            } else {
                vo.setProvince(UNKNOWN);
            }
            if (split.length > 2) {
                vo.setCity(split[2]);
            } else {
                vo.setCity(UNKNOWN);
            }
            vo.setFullLocation(areaAddress);
        }
        redisTemplate.opsForValue().set(ipKey, JSONObject.toJSONString(vo), 2L, TimeUnit.HOURS);

        return vo;
    }

    /**
     * 获取ip地址
     *
     * @param ip
     * @return
     */
    public static IPAddressVO getIpAddressByTencent(String ip) {
        try {
            IPAddressVO vo = new IPAddressVO();
            if (StringUtils.isBlank(ip)) {
                ip = getIp();
            }
            //ip缓存
            String ipStr = ip.replace(".", "").replace(":", "");
            String ipKey = RedisKeyConstant.SYS_IP_KEY + ipStr;
            Object value = redisTemplate.opsForValue().get(ipKey);
            if(Objects.nonNull(value)){
                vo = JSONObject.parseObject(value.toString(), IPAddressVO.class);
                return vo;
            }

            vo.setIp(ip);
            vo.setDevice(getDeviceDetails());

            String url = String.format(CommonConstant.TENCENT_MAP_URL, ip, tencentMapKey);

            ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
            if (HttpStatus.OK != response.getStatusCode()) {
                log.error("获取定位信息失败数据失败{}", response.getBody());
            }

            JSONObject resp = response.getBody();
            Integer status = Objects.requireNonNull(resp).getInteger("status");
            if (CommonConstant.MAP_STATUS_CODE.equals(status) || CommonConstant.MAP_STATUS_CODE_DAY.equals(status)){
                return getIpAddress(ip);
            }

            JSONObject result = resp.getJSONObject("result");
            if(ObjectUtil.isNull(result)){
                return getIpAddress(ip);
            }
            JSONObject location = result.getJSONObject("location");
            vo.setLongitude(location.getString("lng"));
            vo.setLatitude(location.getString("lat"));
            JSONObject adInfo = result.getJSONObject("ad_info");
            String country = adInfo.getString("nation");
            String province = adInfo.getString("province");
            String city = adInfo.getString("city");
            String address = country + "-" + province + "-" + city;
            vo.setCountry(country);
            vo.setProvince(province);
            vo.setCity(city);
            vo.setFullLocation(address);

            redisTemplate.opsForValue().set(ipKey, JSONObject.toJSONString(vo), 2L, TimeUnit.HOURS);
            return vo;
        } catch (RestClientException e) {
            return null;
        }
    }

    private static void setUnknownLocation(IPAddressVO vo) {
        vo.setCountry(UNKNOWN);
        vo.setProvince(UNKNOWN);
        vo.setCity(UNKNOWN);
        vo.setFullLocation(UNKNOWN);
    }
}
