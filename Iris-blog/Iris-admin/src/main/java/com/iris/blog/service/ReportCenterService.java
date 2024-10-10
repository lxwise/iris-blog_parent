package com.iris.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.domain.dto.ReportDownLoadDTO;
import com.iris.blog.domain.dto.ReportCenterDTO;
import com.iris.blog.dao.entity.ReportCenterEntity;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lstar
 * @date: 2024-04
 * @description: 服务类
 */
public interface ReportCenterService extends IService<ReportCenterEntity> {

    /**
     * 查询报表下载中心分页数据
     */
    R getListByPage(PageReq<ReportCenterDTO> req);

    /**
     * 通用下载
     */
    R downloadReport(ReportDownLoadDTO downLoadReportApi, HttpServletRequest request);

}
