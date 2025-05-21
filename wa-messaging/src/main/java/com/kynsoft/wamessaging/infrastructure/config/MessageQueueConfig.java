package com.kynsoft.wamessaging.infrastructure.config;

import com.kynsoft.wamessaging.application.request.MessageStatusNotification;
import com.kynsoft.wamessaging.application.request.SendMessageRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Configuración para las colas internas de mensajes
 */
@Configuration
public class MessageQueueConfig {
    
    /**
     * Cola para solicitudes de envío de mensajes
     */
    @Bean
    public BlockingQueue<SendMessageRequest> messageSendQueue() {
        return new LinkedBlockingQueue<>(1000); // Capacidad configurable
    }
    
    /**
     * Cola para notificaciones de estado de mensajes
     */
    @Bean
    public BlockingQueue<MessageStatusNotification> messageStatusQueue() {
        return new LinkedBlockingQueue<>(1000); // Capacidad configurable
    }
}
