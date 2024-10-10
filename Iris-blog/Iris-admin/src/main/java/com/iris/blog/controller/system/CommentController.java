package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.search.SearchCommentDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.service.CommentService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章评论管理
 */

@Api(tags = "文章评论管理")
@Validated
@RestController
@RequestMapping("/system/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @ApiOperation(value = "文章评论列表", httpMethod = "POST", response = R.class, notes = "文章评论列表")
    @SaCheckLogin
    @PostMapping("/list")
    public R selectCommentList(@RequestBody @Valid  PageReq<SearchCommentDTO> req){

        return commentService.selectCommentList(req);
    }

    @ApiOperation(value = "修改文章评论状态", httpMethod = "POST", response = R.class, notes = "修改文章评论状态")
    @SaCheckPermission("system:comment:update")
    @PostMapping("/status")
    public R updateStatus(@RequestBody @Valid  UpdateCommentStatusDTO dto){
        return commentService.updateStatus(dto);
    }


    @ApiOperation(value = "删除文章评论", httpMethod = "POST", response = R.class, notes = "删除文章评论")
    @OperateLog(value = "删除文章评论")
    @SaCheckPermission("system:comment:delete")
    @PostMapping("/delete")
    public R removeCommentByIds(@RequestBody @NotEmpty List<Long> ids){
        return commentService.removeCommentByIds(ids);
    }

}
