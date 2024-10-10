package com.iris.blog.controller.app;


import com.iris.blog.common.R;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.domain.dto.app.AppLeaveMessageDTO;
import com.iris.blog.domain.vo.LeaveMessageVO;
import com.iris.blog.service.LeaveMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lstar
 * @create 2024-05
 * @description: 留言API-V1
 */
@RestController
@RequestMapping("/v1/message")
@Api(tags = "留言API-V1")
public class AppLeaveMessageController {

    @Resource
    private LeaveMessageService messageService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "留言列表", httpMethod = "GET", response = R.class, notes = "留言列表")
    public R<List<LeaveMessageVO>> selectMessageList(){
        return messageService.selectMessageList();
    }

    @RLimit
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加留言", httpMethod = "POST", response = R.class, notes = "添加留言")
    public R saveMessage(@RequestBody @Validated AppLeaveMessageDTO dto){
        messageService.saveMessage(dto);
        return R.ok();
    }

}

