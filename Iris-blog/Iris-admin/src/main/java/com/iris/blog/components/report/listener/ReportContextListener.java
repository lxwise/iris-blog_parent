package com.iris.blog.components.report.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.components.report.core.ReportDownload;
import com.iris.blog.dao.entity.ReportCenterEntity;
import com.iris.blog.common.enums.ReportStatusEnum;
import com.iris.blog.service.ReportCenterService;
import com.iris.blog.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author lstar
 * @date: 2024-04
 * @description: 报表下载中心监听器,服务重启，下载任务重启成功
 */
@Component
@Slf4j
public class ReportContextListener {

    @Resource
    private ReportCenterService reportCenterService;

    @Resource
    private ReportDownload reportDownload;

    @EventListener(value = {ApplicationReadyEvent.class})
    public void restartListener(){
        log.info("====================>服务重启，下载中心重新开始下载未完成任务");
        //查询大于今天失败的list
        LambdaQueryWrapper<ReportCenterEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ReportCenterEntity::getStatus, ReportStatusEnum.FILED.getStatus())
                .gt(ReportCenterEntity::getCreateTime, DateUtil.getLocalDateTimeStr());
        List<ReportCenterEntity> reportCenterList = reportCenterService.list(queryWrapper);
        if(! reportCenterList.isEmpty()){
            for(ReportCenterEntity reportCenter : reportCenterList){
                reportDownload.ReportDownLoadTask(reportCenter,null);
            }
        }
        log.info("====================>服务重启，下载任务重启成功");
    }
}
