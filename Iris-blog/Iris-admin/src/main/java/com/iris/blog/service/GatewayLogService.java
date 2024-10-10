package com.iris.blog.service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.GatewayLogEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.GatewayLogDTO;
import com.iris.blog.domain.vo.SystemHomeGatewayStatisticsVO;
import com.iris.blog.domain.vo.SystemHomeVisitStatisticsVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网关日志
 */
public interface GatewayLogService extends IService<GatewayLogEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectGatewayLogList(PageReq<GatewayLogDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectGatewayLogById( Long id);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeGatewayLogByIds(List<Long> ids);

    /**
     * 导出
     * @param dto
     * @param response
     */
    void export(GatewayLogDTO dto, HttpServletResponse response);
    /**
     * 读取日志
     *
     * @param path     日志路径
     * @param position 读取位置
     * @param consumer callback
     * @return java.lang.Long
     **/
    Long execute(String path, long position, Consumer<String> consumer);

    /***
     * 保存日志
     * @param logLine
     */
    void saveAccessLog(String logLine);

    /**
     * 一周访问量
     * @return
     */
    List<SystemHomeVisitStatisticsVO>  getUserViewCount();

    /**
     * 网关请求统计
     * @return
     */
    SystemHomeGatewayStatisticsVO requestStatistics();

    /**
     * 访问ip地址
     * @return
     */
    R selectGatewayIPList();
}

