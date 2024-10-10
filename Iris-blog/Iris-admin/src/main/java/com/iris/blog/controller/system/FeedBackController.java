package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchFeedBackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.FeedBackEntity;
import com.iris.blog.domain.dto.FeedBackDTO;
import com.iris.blog.service.FeedBackService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户反馈管理
 */

@Api(tags = "用户反馈管理")
@Validated
@RestController
@RequestMapping("/system/feed/back")
public class FeedBackController {
    @Autowired
    private FeedBackService feedBackService;

    @ApiOperation(value = "用户反馈列表", httpMethod = "POST", response = R.class, notes = "用户反馈列表")
    @SaCheckLogin
    @PostMapping("/list")
    public R selectFeedBackList(@RequestBody @Valid  PageReq<SearchFeedBackDTO> req){

        return feedBackService.selectFeedBackList(req);
    }

    @ApiOperation(value = "修改用户反馈状态", httpMethod = "GET", response = R.class, notes = "修改用户反馈状态")
    @SaCheckPermission("system:feedback:update")
    @GetMapping("/status")
    public R updateStatus(@NotNull Long id){
        return feedBackService.updateStatus(id);
    }

    @ApiOperation(value = "删除用户反馈", httpMethod = "POST", response = R.class, notes = "删除用户反馈")
    @SaCheckPermission("system:feedback:delete")
    @OperateLog(value = "删除用户反馈")
    @PostMapping("/delete")
    public R removeFeedBackByIds(@RequestBody @NotEmpty List<Long> ids){

        return feedBackService.removeFeedBackByIds(ids);
    }

}
