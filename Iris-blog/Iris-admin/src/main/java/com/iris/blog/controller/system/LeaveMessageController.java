package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.search.SearchNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.service.LeaveMessageService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户留言表
 */

@Api(tags = "用户留言管理")
@Validated
@RestController
@RequestMapping("/system/leave/message")
public class LeaveMessageController {
    @Autowired
    private LeaveMessageService leaveMessageService;

    @ApiOperation(value = "用户留言列表", httpMethod = "POST", response = R.class, notes = "用户留言列表")
    @SaCheckLogin
    @PostMapping("/list")
    public R selectLeaveMessageList(@RequestBody @Valid  PageReq<SearchNameDTO> req){

        return leaveMessageService.selectLeaveMessageList(req);
    }
    @ApiOperation(value = "修改用户留言状态", httpMethod = "POST", response = R.class, notes = "修改用户留言状态")
    @SaCheckPermission("system:leave:update")
    @PostMapping("/status")
    public R updateStatus(@RequestBody @Valid UpdateCommentStatusDTO dto){
        return leaveMessageService.updateStatus(dto);
    }

    @ApiOperation(value = "删除用户留言", httpMethod = "POST", response = R.class, notes = "删除用户留言")
    @SaCheckPermission("system:leave:delete")
    @OperateLog(value = "删除用户留言")
    @PostMapping("/delete")
    public R removeLeaveMessageByIds(@RequestBody @NotEmpty List<Long> ids){

        return leaveMessageService.removeLeaveMessageByIds(ids);
    }

}
