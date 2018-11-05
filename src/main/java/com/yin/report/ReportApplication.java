package com.yin.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * main
 *
 * @author yin
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ReportApplication {


    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

    /**
     * 任务配置
     *
     * @return
     */
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("ReportTask-");
        executor.initialize();
        return executor;
    }
}
