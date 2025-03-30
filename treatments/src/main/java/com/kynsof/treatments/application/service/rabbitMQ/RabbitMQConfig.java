// RabbitMQConfig.java en TREATMENT
package com.kynsof.treatments.application.service.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String TREATMENT_QUEUE = "paciente.treatment";

    @Bean
    public Queue treatmentQueue() {
        return new Queue(TREATMENT_QUEUE, true);
    }

    // Este bean se puede inyectar desde otro microservicio si se declara el FanoutExchange globalmente (en patients)
    @Bean
    public Binding bindTreatmentQueue(Queue treatmentQueue) {
        return BindingBuilder.bind(treatmentQueue).to(new FanoutExchange("paciente.exchange", true, false));
    }
}