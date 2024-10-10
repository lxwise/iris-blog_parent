package com.iris.blog.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.iris.blog.common.R;
import com.iris.blog.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author lstar
 * @create 2024-05
 * @description:
 */
@Api(tags = "首页统计")
@Validated
@RestController
@RequestMapping("/system/home")
@Slf4j
public class HomeController {

    @Resource
    private HomeService homeService;

    @GetMapping(value = "/statistics")
    @SaCheckLogin
    @ApiOperation(value = "首页统计信息", httpMethod = "GET", response = R.class, notes = "首页统计信息")
    public R statistics() {
        return homeService.statistics();
    }

    @GetMapping(value = "/data/panel")
    @SaCheckLogin
    @ApiOperation(value = "首页数据看板", httpMethod = "GET", response = R.class, notes = "首页数据看板")
    public R dataPanel() {
        return homeService.dataPanel();
    }

}
