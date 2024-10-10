package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.TalkEntity;
import com.iris.blog.domain.dto.TalkDTO;
import com.iris.blog.service.TalkService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
/**
 * @author lstar
 * @date: 2024-08
 * @description: 说说
 */

@Api(tags = "说说")
@Validated
@RestController
@RequestMapping("/system/talk")
public class TalkController {
    @Autowired
    private TalkService talkService;

    @ApiOperation(value = "列表", httpMethod = "POST", response = R.class, notes = "列表")
    @PostMapping("/list")
    @SaCheckPermission("system:talk:query")
    public R selectTalkList(@RequestBody @Valid  PageReq req){

        return talkService.selectTalkList(req);
    }

    @ApiOperation(value = "信息", httpMethod = "GET", response = R.class, notes = "信息")
    @GetMapping("/info")
    @SaCheckPermission("system:talk:query")
    public R selectTalkById(@NotNull Long id){

        return talkService.selectTalkById(id);
    }

    @ApiOperation(value = "保存", httpMethod = "POST", response = R.class, notes = "保存")
    @PostMapping("/save")
    @SaCheckPermission("system:talk:save")
    public R saveTalk(@RequestBody @Valid  TalkDTO talk){

        return talkService.saveTalk(talk);
    }

    @ApiOperation(value = "修改", httpMethod = "POST", response = R.class, notes = "修改")
    @PostMapping("/update")
    @SaCheckPermission("system:talk:update")
    public R updateTalk(@RequestBody @Valid  TalkDTO talk){

        return talkService.updateTalk(talk);
    }


    @ApiOperation(value = "删除", httpMethod = "POST", response = R.class, notes = "删除")
    @PostMapping("/delete")
    @SaCheckPermission("system:talk:update")
    public R removeTalkByIds(@RequestBody @NotEmpty List<Long> ids){

        return talkService.removeTalkByIds(ids);
    }

}
