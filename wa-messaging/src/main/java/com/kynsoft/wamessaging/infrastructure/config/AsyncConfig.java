package com.kynsoft.wamessaging.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuración de hilos para procesamiento concurrente
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {
    
    @Value("${wa.messaging.thread.core-pool-size}")
    private int corePoolSize;
    
    @Value("${wa.messaging.thread.max-pool-size}")
    private int maxPoolSize;
    
    @Value("${wa.messaging.thread.queue-capacity}")
    private int queueCapacity;
    
    @Value("${wa.messaging.thread.name-prefix}")
    private String namePrefix;
    
    /**
     * Configuración del ThreadPool para el procesamiento de mensajes WhatsApp
     */
    @Bean(name = "messageProcessorExecutor")
    public Executor messageProcessorExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(namePrefix);
        executor.initialize();
        return executor;
    }
}
