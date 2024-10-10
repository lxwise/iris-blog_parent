package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.GatewayLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.blog.domain.vo.GeoVO;
import com.iris.blog.domain.vo.SystemHomeVisitStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网关日志
 */
@Mapper
public interface GatewayLogMapper extends BaseMapper<GatewayLogEntity> {

    List<SystemHomeVisitStatisticsVO> getUserViewCount(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo, @Param("today") LocalDateTime today);

    List<GeoVO> selectGatewayIPList(@Param("today") LocalDateTime today, @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);
}
