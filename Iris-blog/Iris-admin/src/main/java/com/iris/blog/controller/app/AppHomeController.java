package com.iris.blog.controller.app;

import com.iris.blog.common.R;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.domain.vo.SiteConfigVO;
import com.iris.blog.domain.vo.app.AppBlogInfoVO;
import com.iris.blog.domain.vo.app.AppCarouselVO;
import com.iris.blog.service.HomeService;
import com.iris.blog.service.SiteConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author lstar
 * @create 2024-05
 * @description:
 */
@Api(tags = "首页统计")
@Validated
@RestController
@RequestMapping("/v1")
public class AppHomeController {

    @Resource
    private HomeService homeService;
    @GetMapping(value = "/blog")
    @ApiOperation(value = "博客信息", httpMethod = "GET", response = R.class, notes = "博客信息")
    public R<AppBlogInfoVO> blogInfo() {
        return homeService.getSiteConfigInfo();
    }

    @ApiOperation(value = "增加访问量")
    @GetMapping("/report")
    public R<IPAddressVO> report() {
        return homeService.report();
    }


    @ApiOperation(value = "关于我信息", httpMethod = "GET", response = R.class, notes = "关于我信息")
    @GetMapping("/about")
    public R<String> getAboutMe() {
        return homeService.getAboutMe();
    }

    @ApiOperation(value = "轮播图列表")
    @GetMapping("/carousel/list")
    public R<List<AppCarouselVO>> getCarouselList() {
        return R.ok(homeService.getCarouselList());
    }
}
