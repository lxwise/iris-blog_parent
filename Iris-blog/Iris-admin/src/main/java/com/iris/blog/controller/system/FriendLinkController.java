package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.search.SearchFriendLinkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.FriendLinkEntity;
import com.iris.blog.domain.dto.FriendLinkDTO;
import com.iris.blog.service.FriendLinkService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 友情链接表
 */

@Api(tags = "友情链接表")
@Validated
@RestController
@RequestMapping("/system/friend/link")
public class FriendLinkController {
    @Autowired
    private FriendLinkService friendLinkService;

    @ApiOperation(value = "友情链接列表", httpMethod = "POST", response = R.class, notes = "友情链接列表")
    @SaCheckPermission("system:friendlink:query")
    @PostMapping("/list")
    public R selectFriendLinkList(@RequestBody @Valid  PageReq<SearchFriendLinkDTO> req){

        return friendLinkService.selectFriendLinkList(req);
    }

    @ApiOperation(value = "友情链接信息", httpMethod = "GET", response = R.class, notes = "友情链接信息")
    @SaCheckPermission("system:friendlink:query")
    @GetMapping("/info")
    public R selectFriendLinkById(@NotNull Long id){

        return friendLinkService.selectFriendLinkById(id);
    }

    @ApiOperation(value = "保存友情链接", httpMethod = "POST", response = R.class, notes = "保存友情链接")
    @SaCheckPermission("system:friendlink:save")
    @PostMapping("/save")
    public R saveFriendLink(@RequestBody @Valid  FriendLinkDTO friendLink){

        return friendLinkService.saveFriendLink(friendLink);
    }

    @ApiOperation(value = "修改友情链接", httpMethod = "POST", response = R.class, notes = "修改友情链接")
    @SaCheckPermission("system:friendlink:update")
    @PostMapping("/update")
    public R updateFriendLink(@RequestBody @Valid  FriendLinkDTO friendLink){

        return friendLinkService.updateFriendLink(friendLink);
    }


    @ApiOperation(value = "删除友情链接", httpMethod = "POST", response = R.class, notes = "删除友情链接")
    @SaCheckPermission("system:friendlink:delete")
    @OperateLog(value = "删除友情链接")
    @PostMapping("/delete")
    public R removeFriendLinkByIds(@RequestBody @NotEmpty List<Long> ids){

        return friendLinkService.removeFriendLinkByIds(ids);
    }


    @ApiOperation(value = "置顶友链", httpMethod = "GET", response = R.class, notes = "置顶友链")
    @SaCheckPermission("system:friendlink:update")
    @GetMapping(value = "/top")
    public R topFriendLink(@NotNull Long id){
        return friendLinkService.topFriendLink(id);
    }

    @ApiOperation(value = "修改友链状态", httpMethod = "GET", response = R.class, notes = "修改友链状态")
    @SaCheckPermission("system:friendlink:update")
    @GetMapping("/status")
    public R updateStatus(@NotNull Long id,@NotNull Integer status){
        return friendLinkService.updateStatus(id,status);
    }
}
