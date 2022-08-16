package com.example.live.common;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 线程池
 * 使用：
 * 1、在需要执行的方法上加注解@Async("asyncThread") 对应Bean
 * 2、显示调用：
 *     //@Autowired
 *     //@Qualifier("asyncThread")  //指定某个bean
 *     //private Executor taskExecutor;
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/5/19
 */
@Configuration
@EnableAsync
public class ThreadPool implements AsyncConfigurer{

    // ThreadPoolTaskExecutor的处理流程
    // 当池子大小小于corePoolSize，就新建线程，并处理请求
    // 当池子大小等于corePoolSize，把请求放入workQueue中，池子里的空闲线程就去workQueue中取任务并处理
    // 当workQueue放不下任务时，就新建线程入池，并处理请求，如果池子大小撑到了maximumPoolSize，就用RejectedExecutionHandler来做拒绝处理
    // 当池子的线程数大于corePoolSize时，多余的线程会等待keepAliveTime长时间，如果无请求可处理就自行销毁
    @Override
    @Bean("asyncThread")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setThreadNamePrefix("async-thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
