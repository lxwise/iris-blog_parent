package com.iris.blog.components.quartz.task;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务接口，所有定时任务都要实现该接口
 */
public interface ITask {

    /**
     * 系统日志消费
     */
    void operateLogConsumer();

    /**
     * nginx日志解析
     * @param id 配置id
     */
    void parseNginxLog(Long id);

    /**
     * 更新文章统计数据
     */
    void updateArticleStats();
}