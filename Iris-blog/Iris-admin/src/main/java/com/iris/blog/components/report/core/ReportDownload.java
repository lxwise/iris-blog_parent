package com.iris.blog.components.report.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.components.oss.cloud.OssFactory;
import com.iris.blog.dao.entity.ReportCenterEntity;
import com.iris.blog.common.enums.ReportStatusEnum;
import com.iris.blog.dao.mapper.ReportCenterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 报表下载核心类
 */
@Slf4j
@Component
public class ReportDownload {

    @Resource
    private ReportCenterMapper reportCenterMapper;
    @Resource
    private RestTemplate loadBalancedRestTemplate;

    @Async
    public void ReportDownLoadTask(ReportCenterEntity downLoadReportApi, HttpServletRequest request) {
        log.info("收到下载任务，开始执行下载，param=》" + JSONObject.toJSONString(downLoadReportApi));
        StopWatch sw = new StopWatch();
        sw.start();
        String path = downLoadReportApi.getUrl();
        boolean b = true;
        String result = "下载成功";
        String serviceName = downLoadReportApi.getServiceName();
        Path tempFile = null;
        String ossUrl = null;
        Integer available = null;
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String url = "http://" + ip + ":8100/iris" + path;
            ResponseEntity<org.springframework.core.io.Resource> entity = null;
            try {
                // 请求封装
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("FROM", "Y");
                JSONObject jsonParam = JSON.parseObject(downLoadReportApi.getParams());
                if (request != null) {
                    headers.set("Cookie", request.getHeader("Cookie"));
                }
                HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonParam, headers);
                log.info("serviceNotifyUrl:{}", url);
                // 调用获取文件流
                entity = loadBalancedRestTemplate.postForEntity(url, httpEntity, org.springframework.core.io.Resource.class);

            } catch (Exception e) {
                result = "调用hall_service服务出错，请重试，若无法下载，请联系管理员";
                b = false;
                log.error(e.toString());
            }
            if (entity == null || entity.getBody() == null) {
                result = "调用" + serviceName + "服务出错，请重试，若无法下载，请联系管理员";
                b = false;
                log.error("下载失败，请检查参数");
            }

            InputStream inputStreamOss = entity.getBody().getInputStream();
            tempFile = Files.createTempFile("report_" + UUID.randomUUID() + "_", ".tmp");
            Files.copy(inputStreamOss, tempFile, StandardCopyOption.REPLACE_EXISTING);
            FileInputStream fileInputStream = new FileInputStream(tempFile.toFile());
            available = fileInputStream.available();
            log.info("文件大小:{}", available + "bytes");
            String suffix = downLoadReportApi.getSuffix();
            if (StringUtils.isEmpty(suffix)) {
                suffix = "xlsx";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = downLoadReportApi.getCreateTime().format(formatter);
            String ossPath = OssFactory.build().getPath("fileCenter", suffix, downLoadReportApi.getName() + "," + formattedDateTime);
            ossUrl = OssFactory.build().upload(fileInputStream, ossPath);
            log.info(" 存入oss成功，url=》" + url);
        } catch (Exception e) {
            log.error("出错啦", e);
            result = "调用服务出错，请重试，若无法下载，请联系管理员";
            b = false;
            log.error("下载失败，请检查参数");
        } finally {
            if (tempFile != null) {
                try {
                    Files.delete(tempFile);
                } catch (IOException e) {
                    throw new BusinessException(ResultCode.ERROR);
                }
            }
        }
        if (b) {
            // 下载成功
            // 更新下载中心数据
            downLoadReportApi.setStatus(ReportStatusEnum.COMPLETED.getStatus());
            downLoadReportApi.setOssAddress(ossUrl);
            downLoadReportApi.setFileSize(available);
            downLoadReportApi.setResult(result);
        } else {
            downLoadReportApi.setStatus(ReportStatusEnum.FILED.getStatus());
            downLoadReportApi.setResult("下载失败，失败原因：" + result);
        }
        // 处理时长(秒)
        sw.stop();
        long minutes = sw.getTotalTimeMillis() / 1000;
        downLoadReportApi.setDuration(minutes);
        reportCenterMapper.updateById(downLoadReportApi);
        log.info("下载中心：文件下载成功==>" + JSONObject.toJSONString(downLoadReportApi));

    }
}
