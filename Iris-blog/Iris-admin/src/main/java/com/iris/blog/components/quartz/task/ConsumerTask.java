package com.iris.blog.components.quartz.task;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.ArticleEntity;
import com.iris.blog.dao.entity.GatewayConfigEntity;
import com.iris.blog.dao.entity.OperateLogEntity;
import com.iris.blog.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author lstar
 * @create 2024-04
 * @description:
 */

@Slf4j
@Component("consumerTask")
public class ConsumerTask implements ITask{

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private OperateLogService operateLogService;
    @Resource
    private GatewayConfigService gatewayConfigService;
    @Resource
    private GatewayLogService gatewayLogService;

    @Resource
    private ArticleService articleService;

    @Override
    public void operateLogConsumer() {
        if (this.redisUtil.getQueueSize(RedisKeyConstant.SYS_LOG_KEY).compareTo(1L) < 0) {
            return;
        }
        receiveQueue();
    }

    @Override
    public void parseNginxLog(Long id) {
        log.debug("执行解析NX日志任务开始");
        try {
            GatewayConfigEntity configEntity = gatewayConfigService.getById(id);
            if (configEntity == null || configEntity.getId() == null) {
                return;
            }

            Long position = gatewayLogService.execute(configEntity.getPath(),
                    configEntity.getPosition(),
                    (logLine) -> gatewayLogService.saveAccessLog(logLine));
            // 更新读取点位置
            gatewayConfigService.lambdaUpdate()
                    .set(GatewayConfigEntity::getPosition, position)
                    .eq(GatewayConfigEntity::getId, id)
                    .update();
        } catch (Exception e) {
            log.error("执行解析NX日志任务出错啦,错误:{}",e.getMessage(),e);
        }
        log.debug("执行解析NX日志任务结束");
    }

    @Override
    public void updateArticleStats() {
        try {
            // 合并点赞量和阅读量的更新
            Map<Long, ArticleEntity> articleMap = new HashMap<>();

            // 更新文章点赞量
            Map<String, Integer> articleLikeMap = redisUtil.getHashKeyAll(RedisKeyConstant.ARTICLE_LIKE_COUNT);
            if (CollectionUtils.isNotEmpty(articleLikeMap)) {
                articleLikeMap.forEach((k, v) -> {
                    Long articleId = Long.valueOf(k);
                    ArticleEntity articleEntity = articleMap.computeIfAbsent(articleId, id -> new ArticleEntity().setId(articleId));
                    articleEntity.setLikes(v);
                });
            }

            // 更新文章阅读量
            Map<String, Integer> articleViewMap = redisUtil.getHashKeyAll(RedisKeyConstant.ARTICLE_VIEW_COUNT);
            if (CollectionUtils.isNotEmpty(articleViewMap)) {
                articleViewMap.forEach((k, v) -> {
                    Long articleId = Long.valueOf(k);
                    ArticleEntity articleEntity = articleMap.computeIfAbsent(articleId, id -> new ArticleEntity().setId(articleId));
                    articleEntity.setViews(v);
                });
            }

            articleService.updateArticleStats(new ArrayList<>(articleMap.values()));
        } catch (Exception e) {
            log.error("执行更新文章统计信息出错啦",e);
        }
    }

    /**
     * 操作日志消费队列
     */
    private void receiveQueue() {
        log.debug("执行日志消费任务开始");
        try {
            threadPoolTaskExecutor.setThreadNamePrefix("log-consumer-schedule-pool-");
            String key = RedisKeyConstant.SYS_LOG_KEY;

            int count = 100;
            while (count>0) {
                OperateLogEntity logEntity = redisUtil.getPopOneFromQueueRight(key, OperateLogEntity.class);
                if (logEntity == null) {
                    break;
                }
                threadPoolTaskExecutor.submit(() -> {
                    log.info("消费日志的线程名字:{}",Thread.currentThread().getName());
                    operateLogService.save(logEntity);
                });
                count--;
                Thread.sleep(50);
                log.info("执行保存系统日志第{}次",100-count);
            }
        } catch (Exception e) {
            log.error("执行日志消费任务出错啦");
        } finally {
            threadPoolTaskExecutor.shutdown();
        }
        log.debug("执行日志消费任务完成");
    }
}
