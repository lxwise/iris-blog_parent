package com.iris.blog.controller.app;

import cn.dev33.satoken.annotation.SaCheckLogin;

import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.domain.dto.app.AppFeedBackDTO;
import com.iris.blog.domain.search.SearchFeedBackDTO;
import com.iris.blog.service.FeedBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lstar
 * @create 2024-05
 * @description: 反馈API-V1
 */
@RestController
@RequestMapping("/v1/feedback")
@Api(tags = "反馈API-V1")
public class AppFeedBackController {
    @Resource
    private FeedBackService feedBackService;
    @SaCheckLogin
    @PostMapping(value = "/save")
    @ApiOperation(value = "添加反馈", httpMethod = "POST", response = R.class, notes = "添加反馈")
    public R saveFeedback(@RequestBody AppFeedBackDTO dto) {
        return  feedBackService.saveFeedback(dto);
    }
    @ApiOperation(value = "反馈列表", httpMethod = "POST", response = R.class, notes = "反馈列表")
    @PostMapping("/list")
    public R selectFeedBackList(@RequestBody @Valid PageReq<SearchFeedBackDTO> req){
        return feedBackService.selectFeedBackList(req);
    }
}
