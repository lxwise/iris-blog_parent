package com.iris.blog.controller.app;

import com.iris.blog.common.R;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.domain.dto.FriendLinkDTO;
import com.iris.blog.domain.dto.app.AppApplyFriendLinkDTO;
import com.iris.blog.domain.vo.FriendLinkVO;
import com.iris.blog.service.FriendLinkService;
import com.iris.blog.utils.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * @author lstar
 * @create 2024-05
 * @description: 友情链接API
 */
@Api(tags = "友情链接API-V1")
@RestController
@RequestMapping("/v1/friend/link")
public class AppFriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;

    @RequestMapping(value = "/list")
    @ApiOperation(value = "友链列表", httpMethod = "GET", response = R.class, notes = "友链列表")
    @RLimit
    public R<List<FriendLinkVO>>  selectFriendLinkList(){
        return friendLinkService.friendLinkList();
    }

    @RequestMapping(value = "/apply")
    @ApiOperation(value = "申请友链", httpMethod = "POST", response = R.class, notes = "申请友链")
    @RLimit(count = 3, time = 7200)
    public R applyFriendLink(@RequestBody @Valid AppApplyFriendLinkDTO friendLink){
        return friendLinkService.saveFriendLink(BeanUtil.newBean(friendLink, FriendLinkDTO.class));
    }



}

