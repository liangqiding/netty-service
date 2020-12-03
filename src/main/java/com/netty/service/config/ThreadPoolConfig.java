package com.netty.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 梁其定
 * @DateTime: 2020/4/10 0010 14:13
 * @Description: TODO 启动线程池执行
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean("consumerPool")
    public TaskExecutor consumerPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(2);
        // 设置最大线程数
        executor.setMaxPoolSize(4);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("线程池kafkaHandle-");
        /*
        todo 设置拒绝策略
         当抛出RejectedExecutionException异常时，会调rejectedExecution方法 调用者运行策略实现了一种调节机制，
         该策略既不会抛弃任务也不会爆出异常，而是将任务退回给调用者，从而降低新任务的流量
        */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    @Bean("ConsumerHandlerPool")
    public TaskExecutor ConsumerHandlerPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(3);
        // 设置最大线程数
        executor.setMaxPoolSize(6);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("线程池kafkaHandle-");
        /*
        todo 设置拒绝策略
         当抛出RejectedExecutionException异常时，会调rejectedExecution方法 调用者运行策略实现了一种调节机制，
         该策略既不会抛弃任务也不会爆出异常，而是将任务退回给调用者，从而降低新任务的流量
        */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
