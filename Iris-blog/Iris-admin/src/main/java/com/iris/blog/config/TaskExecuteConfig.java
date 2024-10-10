package com.iris.blog.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author
 * 自定义线程池的配置类
 */
@Configuration
@EnableAsync
public class TaskExecuteConfig {

    @Value("${thread.pool.core_pool_size}")
    private int corePoolSize;
    @Value("${thread.pool.max_pool_size}")
    private int maxPoolSize;
    @Value("${thread.pool.queue_capacity}")
    private int queueCapacity;
    @Value("${thread.pool.keep_alive_time}")
    private int keepAliveTime;

    @Bean
    public ThreadPoolTaskExecutor SendTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        ///配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        /**
         * 线程池维护线程所允许的空闲时间--单位秒，超过销毁
         * 线程池线程数量大于corePoolSize时候，多出来的空闲线程，多长时间会被销毁
         */
        executor.setKeepAliveSeconds(keepAliveTime);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("pool-task-executor");
        /**
         * 线程池拒绝策略
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        return executor;

    }
}
