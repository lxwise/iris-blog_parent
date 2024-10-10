package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.CarouselDTO;
import com.iris.blog.service.CarouselService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-08
 * @description: 首页轮播
 */

@Api(tags = "首页轮播")
@Validated
@RestController
@RequestMapping("/system/carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @ApiOperation(value = "列表", httpMethod = "POST", response = R.class, notes = "列表")
    @PostMapping("/list")
    @SaCheckPermission("system:carousel:query")
    public R selectCarouselList(@RequestBody @Valid  PageReq req){

        return carouselService.selectCarouselPageList(req);
    }

    @ApiOperation(value = "保存", httpMethod = "POST", response = R.class, notes = "保存")
    @PostMapping("/save")
    @SaCheckPermission("system:carousel:save")
    public R saveCarousel(@RequestBody @Valid  CarouselDTO carousel){

        return carouselService.saveCarousel(carousel);
    }


    @ApiOperation(value = "删除", httpMethod = "POST", response = R.class, notes = "删除")
    @PostMapping("/delete")
    @SaCheckPermission("system:carousel:delete")
    public R removeCarouselByIds(@RequestBody @NotEmpty List<Integer> ids){

        return carouselService.removeCarouselByIds(ids);
    }

    @ApiOperation(value = "修改状态", httpMethod = "GET", response = R.class, notes = "修改状态")
    @GetMapping("/update/status")
    @SaCheckPermission("system:carousel:update")
    public R updateStatusById(@NotNull Long id){

        return carouselService.updateStatusById(id);
    }

}
