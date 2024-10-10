package com.iris.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.domain.vo.*;
import com.iris.blog.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Today;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.GatewayLogMapper;
import com.iris.blog.dao.entity.GatewayLogEntity;
import com.iris.blog.domain.dto.GatewayLogDTO;
import com.iris.blog.service.GatewayLogService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.function.Consumer;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网关日志
 */
@Service("gatewayLogService")
@Slf4j
public class GatewayLogServiceImpl extends ServiceImpl<GatewayLogMapper, GatewayLogEntity> implements GatewayLogService {

    @Override
    public R selectGatewayLogList(PageReq<GatewayLogDTO> req){

        GatewayLogDTO action = req.getAction();
        GatewayLogEntity entity = BeanUtil.newBean(action, GatewayLogEntity.class);
        Page<GatewayLogEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .gt(action.getCreateTimeStart() != null,GatewayLogEntity::getRequestEndTime,action.getCreateTimeStart())
                        .lt(action.getCreateTimeEnd() != null,GatewayLogEntity::getRequestEndTime,action.getCreateTimeEnd())
                        .orderByDesc(GatewayLogEntity::getId));

        PageBean<GatewayLogVO> pageBean = PageUtil.pageBean(page, GatewayLogVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectGatewayLogById( Long id){
        GatewayLogEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        GatewayLogVO vo = BeanUtil.newBean(entity, GatewayLogVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeGatewayLogByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }
    @Override
    public void export(GatewayLogDTO dto, HttpServletResponse response) {
        try {
            GatewayLogEntity entity = BeanUtil.newBean(dto, GatewayLogEntity.class);
            List<GatewayLogEntity> list = this.list(Wrappers.lambdaQuery(entity)
                    .gt(dto.getCreateTimeStart() != null,GatewayLogEntity::getCreateTime,dto.getCreateTimeStart())
                    .lt(dto.getCreateTimeEnd() != null,GatewayLogEntity::getCreateTime,dto.getCreateTimeEnd())
                    .orderByDesc(GatewayLogEntity::getId));
            ExcelUtils.write(response, "网关日志", "网关日志", GatewayLogVO.class, list.stream().map(item -> BeanUtil.newBean(item, GatewayLogVO.class)).toList());
        } catch (IOException e) {
            throw new BusinessException(ResultCode.REPORT_EXPORT_ERROR);
        }
    }

    /**
     * 读取日志
     *
     * @param path     日志路径
     * @param position 读取位置
     * @param consumer callback
     * @return java.lang.Long
     **/
    @Override
    public Long execute(String path, long position, Consumer<String> consumer) {
        // 结束点
        long endPosition = position;
        try (FileChannel fc = new FileInputStream(path).getChannel();
             ByteArrayOutputStream byteArrBuilder = new ByteArrayOutputStream()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            log.debug("本次读取[{}]文件起始位置：{} .", path, position);
            fc.position(position);
            while (fc.read(buffer) > 0) {
                endPosition = fc.position();
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    if ((char) b == '\n') {
                        byte[] bytes = byteArrBuilder.toByteArray();
                        consumer.accept(new String(bytes));
                        byteArrBuilder.reset();
                    } else {
                        byteArrBuilder.write(b);
                    }
                }
                // Clear the buffer for the next read
                buffer.clear();
            }
        } catch (Exception e) {
            log.error("解析Nginx日志异常:{}", e.getMessage(), e);
            throw new BusinessException("解析Nginx日志异常");
        }
        log.debug("本次读取[{}]文件结束位置：{} .", path, endPosition);
        return endPosition;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAccessLog(String logLine) {

        if(StringUtils.isBlank(logLine)){
            return;
        }
        GatewayAccessLogVO logVO = JSONObject.parseObject(logLine, GatewayAccessLogVO.class);
        if(Objects.isNull(logVO)){
            return;
        }
        GatewayLogEntity entity = BeanUtil.newBean(logVO, GatewayLogEntity.class);
        entity.setRequestEndTime(logVO.getTime());
        entity.setStatusCode(logVO.getStatus());
        entity.setRequestMethod(logVO.getRm());
        entity.setServerProtocol(logVO.getProtocol());
        entity.setServerPort(logVO.getPort());
        entity.setBytesSent(logVO.getSize());
        entity.setRequestTime(logVO.getRt());
        entity.setHttpUserAgent(logVO.getAgent());
        IPAddressVO vo = IPUtils.getIpAddressByTencent(logVO.getIp());
        if(Objects.nonNull(vo)){
         if(StringUtils.isNotBlank(vo.getLatitude()) && StringUtils.isNotBlank(vo.getLongitude())){
             entity.setLatitude(vo.getLatitude());
             entity.setLongitude(vo.getLongitude());
         }
        }
        this.save(entity);
    }

    @Override
    public List<SystemHomeVisitStatisticsVO> getUserViewCount() {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = today.minus(7, ChronoUnit.DAYS);
        return this.baseMapper.getUserViewCount(sevenDaysAgo, today);
    }

    @Override
    public SystemHomeGatewayStatisticsVO requestStatistics() {
        SystemHomeGatewayStatisticsVO build = SystemHomeGatewayStatisticsVO.builder()
                .urlCount(0)
                .ipCount(0)
                .dataSize(BigDecimal.ZERO)
                .avgRespTime(BigDecimal.ZERO)
                .build();
        try {
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime sevenDaysAgo = today.minusDays(7);
            List<GatewayLogEntity> list = this.list(lambdaQuery().getWrapper().ge(GatewayLogEntity::getRequestEndTime, sevenDaysAgo).le(GatewayLogEntity::getRequestEndTime, today));
            if(CollectionUtils.isEmpty(list)){
                return build;
            }
            build.setUrlCount(list.size());

            List<GatewayLogEntity> distinct = LambdaUtil.listDistinctBy(list, GatewayLogEntity::getIp);
            build.setIpCount(distinct.size());
            //响应数据大小
            long data = LambdaUtil.listToSumLongBy(list, GatewayLogEntity::getBytesSent);
            BigDecimal dataSize = new BigDecimal(data).divide(new BigDecimal(1024), 2, RoundingMode.HALF_UP);
            build.setDataSize(dataSize);

            //接口响应时长
            double sumTime = list.stream().mapToDouble(item -> Double.parseDouble(item.getRequestTime())).sum();
            if(sumTime > 0){
                BigDecimal setAvgRespTime = new BigDecimal(sumTime).divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
                build.setAvgRespTime(setAvgRespTime);
            }
            return build;
        } catch (Exception e) {
            return build;
        }
    }

    @Override
    public R selectGatewayIPList() {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = today.minus(7, ChronoUnit.DAYS);

        List<GeoVO> list = this.baseMapper.selectGatewayIPList(today,sevenDaysAgo);


        return R.ok(list);
    }

    public GatewayLogEntity checkExist(Long id){
        GatewayLogEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}