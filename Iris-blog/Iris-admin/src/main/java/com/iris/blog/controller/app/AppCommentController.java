package com.iris.blog.controller.app;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.db.PageResult;
import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.common.enums.LikeTypeEnum;
import com.iris.blog.domain.dto.app.AppCommentDTO;
import com.iris.blog.domain.search.SearchCommentDTO;
import com.iris.blog.domain.search.app.AppSearchCommentDTO;
import com.iris.blog.domain.vo.app.AppCommentVO;
import com.iris.blog.domain.vo.app.AppRecentCommentLatestVO;
import com.iris.blog.domain.vo.app.AppReplyCommentVO;
import com.iris.blog.service.CommentService;
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
 * @description: 评论API-V1
 */
@Validated
@RestController
@RequestMapping("/v1/comment")
@Api(tags = "评论API-V1")
public class AppCommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private LikeStrategyContext likeStrategyContext;

    @ApiOperation(value = "最新评论列表", httpMethod = "GET", response = R.class, notes = "最新评论列表")
    @GetMapping("/latest/list")
    @RLimit
    public R<List<AppRecentCommentLatestVO>> latestRecentCommentList(){
        return this.commentService.latestRecentCommentList();
    }

    @RLimit
    @SaCheckLogin
    @ApiOperation(value = "添加评论", httpMethod = "POST", response = R.class, notes = "添加评论")
    @PostMapping("/comment/add")
    public R addComment(@Validated @RequestBody AppCommentDTO dto) {
        this.commentService.addComment(dto);
        return R.ok();
    }

    @SaCheckLogin
    @ApiOperation(value = "点赞评论", httpMethod = "POST", response = R.class, notes = "点赞评论")
    @RLimit(count = 3, time = 60)
    @PostMapping("/comment/{commentId}/like")
    public R likeComment(@PathVariable("commentId") Integer commentId) {
        likeStrategyContext.executeLikeStrategy(LikeTypeEnum.COMMENT, commentId);
        return R.ok();
    }

    @ApiOperation(value = "查看评论", httpMethod = "POST", response = R.class, notes = "查看评论")
    @PostMapping("/comment/list")
    @RLimit
    public R<PageBean<AppCommentVO>> viewCommentList(@RequestBody @Valid PageReq<AppSearchCommentDTO> req) {
        return this.commentService.viewCommentList(req);
    }

    @ApiOperation(value = "查看回复评论", httpMethod = "POST", response = R.class, notes = "查看回复评论")
    @PostMapping("/comment/reply/list")
    @RLimit
    public R<PageBean<AppReplyCommentVO>> viewReplyCommentList(@RequestBody @Valid PageReq<AppSearchCommentDTO> req) {
        return this.commentService.viewReplyCommentList(req);
    }
}
