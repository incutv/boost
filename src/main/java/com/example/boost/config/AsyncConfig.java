package com.example.boost.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "asyncExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);         // 동시에 최소 10개 스레드
        executor.setMaxPoolSize(30);          // 최대 50개까지 증가
        executor.setQueueCapacity(100);      // 큐 대기열 용량
        executor.setThreadNamePrefix("Async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 기본적으로 ThreadPoolTaskExecutor는 작업을 수용할 수 없을 때 AbortPolicy를 사용하여 예외를 발생시킵니다. 이를 CallerRunsPolicy로 변경하면, 거부된 작업을 호출한 스레드에서 직접 실행하게 되어 예외 발생을 방지할 수 있습니다.
        executor.initialize();
        return executor;
    }
}

