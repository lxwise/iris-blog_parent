package com.iris.blog.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.common.constant.CommonConstant;
import com.iris.blog.domain.vo.SessionUserVO;
import com.iris.blog.domain.dto.ReportDownLoadDTO;
import com.iris.blog.domain.dto.ReportCenterDTO;
import com.iris.blog.service.ReportCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 报表下载中心
 */
@Api(tags = "报表下载中心")
@Validated
@RestController
@RequestMapping("/system/report")
public class ReportCenterController {

    @Resource
    private ReportCenterService reportCenterService;

    @ApiOperation(value = "查询报表下载中心分页数据", httpMethod = "POST", response = R.class, notes = "查询报表下载中心分页数据")
    @SaCheckLogin
    @PostMapping("/page")
    public R page(@RequestBody @Valid PageReq<ReportCenterDTO> req){
        return reportCenterService.getListByPage(req);
    }

    @ApiOperation(value = "通用下载", httpMethod = "POST", response = R.class, notes = "通用下载")
    @SaCheckLogin
    @PostMapping("/download/file")
    public R downloadReport(@RequestBody @Valid ReportDownLoadDTO downLoadReportApi, HttpServletRequest request){
        SessionUserVO user = (SessionUserVO) StpUtil.getSession().get(CommonConstant.LOGIN_USER);
        downLoadReportApi.setUserId(user.getId());
        downLoadReportApi.setUserName(user.getUsername());
        return reportCenterService.downloadReport(downLoadReportApi,request);
    }

}
