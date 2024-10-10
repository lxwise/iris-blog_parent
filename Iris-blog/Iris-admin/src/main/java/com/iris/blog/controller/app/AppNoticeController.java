package com.iris.blog.controller.app;

import javax.annotation.Resource;

import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.NoticeDTO;
import com.iris.blog.domain.vo.NoticeVO;
import com.iris.blog.domain.vo.app.AppNoticeCountVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.service.NoticeService;
import com.iris.blog.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * @author lstar
 * @date: 2024-09
 * @description: 系统通知
 */

@Api(tags = "系统通知-API")
@Validated
@RestController
@RequestMapping("/v1/notice")
public class AppNoticeController {
    @Resource
    private NoticeService noticeService;

    @ApiOperation(value = "查看通知", httpMethod = "GET", response = R.class, notes = "查看通知")
    @PostMapping("/list")
    public R<PageBean<NoticeVO>> getNoticeList(@RequestBody @Valid PageReq<NoticeDTO> pageReq){
        return noticeService.getNoticeList(pageReq);
    }
    @ApiOperation(value = "清空通知", httpMethod = "GET", response = R.class, notes = "清空通知")
    @GetMapping("/clear")
    public R clearAllNotice(@NotNull Integer type){
         noticeService.clearAllNotice(type);
         return R.ok();
    }

    @ApiOperation(value = "未读消息数", httpMethod = "GET", response = R.class, notes = "未读消息数")
    @GetMapping("/read")
    public R<AppNoticeCountVO> readAllNotice(){
        return noticeService.readAllNotice();
    }

}
