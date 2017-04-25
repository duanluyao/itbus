package cn.dubby.itbus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
@Configuration
public class TaskExecutorConfig {

    @Bean(name = "emailTaskExecutor")
    public Executor emailTaskExecutor() {
        return Executors.newFixedThreadPool(2);
    }

}
