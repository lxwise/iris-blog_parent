package com.iris.blog.controller.app;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.common.enums.LikeTypeEnum;
import com.iris.blog.domain.vo.TalkVO;
import com.iris.blog.service.TalkService;
import com.iris.blog.strategy.context.LikeStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lstar
 * @create 2024-05
 * @description: 说说接口-API
 */
@Api(tags = "说说接口-API")
@Validated
@RestController
@RequestMapping("/v1/talk")
public class AppTalkController {

    @Resource
    private TalkService talkService;

    @Resource
    private LikeStrategyContext likeStrategyContext;

    @ApiOperation(value = "查看首页说说", httpMethod = "GET", response = R.class, notes = "查看首页说说")
    @GetMapping("/home/talk")
    public R<List<String>> listTalkHome() {
        return this.talkService.listTalkHome();
    }

    @SaCheckLogin
    @ApiOperation(value = "点赞说说", httpMethod = "POST", response = R.class, notes = "点赞说说")
    @RLimit(count = 3, time = 60)
    @PostMapping("/talk/{talkId}/like")
    public R<?> saveTalkLike(@PathVariable("talkId") Integer talkId) {
        likeStrategyContext.executeLikeStrategy(LikeTypeEnum.TALK, talkId);
        return R.ok();
    }

    @ApiOperation(value = "查看说说列表", httpMethod = "POST", response = R.class, notes = "查看说说列表")
    @PostMapping("/talk/list")
    public R<PageBean<TalkVO>> talkList(@RequestBody @Valid PageReq req) {
        return talkService.talkList(req);
    }

    @ApiOperation(value = "查看说说", httpMethod = "GET", response = R.class, notes = "查看说说")
    @GetMapping("/talk/{talkId}")
    public R<TalkVO> getTalkById(@PathVariable("talkId") Integer talkId) {
        return talkService.getTalkById(talkId);
    }
}
