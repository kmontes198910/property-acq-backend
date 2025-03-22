package com.kynsof.treatments.infrastructure.config.rabbitMq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.treatment.name}")
    private String treatmentQueueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    // Declarar la cola de tratamientos
    @Bean
    public Queue treatmentQueue() {
        return new Queue(treatmentQueueName, true); // Cola durable
    }

    // Declarar el Fanout Exchange
    @Bean
    public FanoutExchange pacienteExchange() {
        return new FanoutExchange(exchangeName, true, false);
    }

    // Vincular la cola con el exchange
    @Bean
    public Binding bindingTreatmentQueue(Queue treatmentQueue, FanoutExchange pacienteExchange) {
        return BindingBuilder.bind(treatmentQueue).to(pacienteExchange);
    }
}