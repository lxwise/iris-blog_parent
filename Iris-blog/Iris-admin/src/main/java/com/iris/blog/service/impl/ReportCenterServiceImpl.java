package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.components.report.core.ReportDownload;
import com.iris.blog.domain.dto.ReportDownLoadDTO;
import com.iris.blog.domain.dto.ReportCenterDTO;
import com.iris.blog.domain.vo.ReportCenterVO;
import com.iris.blog.dao.entity.ReportCenterEntity;
import com.iris.blog.dao.mapper.ReportCenterMapper;
import com.iris.blog.service.ReportCenterService;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 服务实现类
 */
@Slf4j
@Service
public class ReportCenterServiceImpl extends ServiceImpl<ReportCenterMapper, ReportCenterEntity> implements ReportCenterService {

    @Resource
    private ReportDownload reportDownload;

    @Override
    public R getListByPage(PageReq<ReportCenterDTO> req) {


        ReportCenterEntity entity = BeanUtil.newBean(req.getAction(), ReportCenterEntity.class);
        Page<ReportCenterEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(ReportCenterEntity::getId));

        if (page.getRecords() != null && !page.getRecords().isEmpty()) {
            List<ReportCenterEntity> records = page.getRecords();
            for (ReportCenterEntity record : records) {
                record.setFileSize(record.getFileSize() == null ? 0 : record.getFileSize());
                record.setFileSizeName(new BigDecimal(record.getFileSize()).divide(new BigDecimal(1024 * 1024), 2, RoundingMode.HALF_UP));
            }
        }
        PageBean<ReportCenterVO> pageBean = PageUtil.pageBean(page, ReportCenterVO.class);
        return R.ok(pageBean);
    }

    @Override
    public R<String> downloadReport(ReportDownLoadDTO downLoadReportApi, HttpServletRequest request) {
        ReportCenterEntity reportCenter = new ReportCenterEntity();
        reportCenter.setName(downLoadReportApi.getFileName());
        reportCenter.setServiceName(downLoadReportApi.getServiceName());
        reportCenter.setUrl(downLoadReportApi.getUrl());
        reportCenter.setSuffix(downLoadReportApi.getSuffix());
        reportCenter.setOperatorId(downLoadReportApi.getUserId());
        reportCenter.setOperatorName(downLoadReportApi.getUserName());
        reportCenter.setParams(downLoadReportApi.getParams().toJSONString());
        reportCenter.setCreateTime(LocalDateTime.now());
        this.baseMapper.insert(reportCenter);
        //异步调用
        reportDownload.ReportDownLoadTask(reportCenter, request);

        return R.ok("已发起下载任务，请前往下载中心下载");
    }

}
