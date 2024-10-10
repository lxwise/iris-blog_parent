package com.iris.blog.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.SiteConfigDTO;
import com.iris.blog.service.SiteConfigService;
import com.iris.blog.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网站配置
 */

@Api(tags = "网站配置")
@Validated
@RestController
@RequestMapping("/system/site/config")
public class SiteConfigController {
    @Autowired
    private SiteConfigService siteConfigService;

    @ApiOperation(value = "获取网站配置信息", httpMethod = "GET", response = R.class, notes = "获取网站配置信息")
    @GetMapping("/selectSiteConfigInfo")
    public R getSiteConfigInfo(){

        return R.ok(siteConfigService.getSiteConfigInfo());
    }

    @ApiOperation(value = "保存", httpMethod = "POST", response = R.class, notes = "保存")
    @PostMapping("/save")
    public R saveSiteConfig(@RequestBody @Valid  SiteConfigDTO siteConfig){

        return siteConfigService.saveSiteConfig(siteConfig);
    }

}
