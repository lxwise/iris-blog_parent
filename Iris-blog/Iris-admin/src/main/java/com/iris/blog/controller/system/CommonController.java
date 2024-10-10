package com.iris.blog.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iris.blog.common.R;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.components.quartz.task.ConsumerTask;
import com.iris.blog.dao.entity.RoleEntity;
import com.iris.blog.dao.entity.UserEntity;
import com.iris.blog.domain.dto.RoleDTO;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.domain.vo.weather.WeatherVO;
import com.iris.blog.service.CommonService;
import com.iris.blog.service.RoleService;
import com.iris.blog.utils.IPUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;


/**
 * @author lstar
 * @create 2024-05
 * @description:
 */
@Api(tags = "公共工具接口")
@Validated
@RestController
@RequestMapping("/system/common")
@Slf4j
public class CommonController {

    @Resource
    private CommonService commonService;

//    @SaCheckLogin
    @GetMapping("/get/weather")
    public R<WeatherVO> getWeatherInfo(String cityCode) {

        return commonService.getWeatherInfo(cityCode);
    }
    @SaCheckLogin
    @GetMapping("/get/ip")
    public R<IPAddressVO> getAreaByIp(String ip) {
        // 获得城市
//        IPAddressVO ipAddress = IPUtils.getIpAddress(ip);
        IPAddressVO ipAddress = IPUtils.getIpAddressByTencent(ip);
        return R.ok(ipAddress);
    }
}
