package com.iris.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.iris.blog.common.R;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.domain.vo.RedisMonitorRespVO;
import com.iris.blog.domain.vo.SystemServerInfoVO;
import com.iris.blog.service.MonitorService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lstar
 * @create 2024-04
 * @description:
 */
@Service
@Slf4j
public class MonitorServiceImpl implements MonitorService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RestTemplate restTemplate;
    @Override
    public R getSystemServerInfo() {
        SystemServerInfoVO serverInfoVO = new SystemServerInfoVO();
        try {
            serverInfoVO.copyTo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(serverInfoVO);
    }

    @Override
    public R getRedisMonitorInfo() {

        // 获得 Redis 统计信息
        Properties info = stringRedisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        Long dbSize = stringRedisTemplate.execute(RedisServerCommands::dbSize);
        Properties commandStats = stringRedisTemplate.execute((
                RedisCallback<Properties>) connection -> connection.info("commandstats"));

        RedisMonitorRespVO respVO = RedisMonitorRespVO.builder().info(info).dbSize(dbSize)
                .commandStats(new ArrayList<>(Objects.requireNonNull(commandStats).size())).build();
        commandStats.forEach((key, value) -> {
            respVO.getCommandStats().add(RedisMonitorRespVO.CommandStat.builder()
                    .command(StrUtil.subAfter((String) key, "cmdstat_", false))
                    .calls(Long.valueOf(StrUtil.subBetween((String) value, "calls=", ",")))
                    .usec(Long.valueOf(StrUtil.subBetween((String) value, "usec=", ",")))
                    .build());
        });
        return R.ok(respVO);
    }

    @SneakyThrows
    @Override
    public R getServiceInfo() {
        ResponseEntity<JSONObject> resp = restTemplate.getForEntity("http://127.0.0.1:8100/iris/system/health", JSONObject.class);
        if (HttpStatus.OK != resp.getStatusCode()) {
            throw new BusinessException("获取服务健康信息失败");
        }
        return R.ok(resp.getBody());
    }
}
