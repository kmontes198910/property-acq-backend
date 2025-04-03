package com.kynsof.treatments.application.service.rabbitMQ.patientCreate;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPatientCreateConfig {

    private static final String CREATE_QUEUE_NAME = "paciente.treatment";
    private static final String CREATE_EXCHANGE_NAME = "paciente.exchange";

    @Bean(name = "createTreatmentQueue")
    public Queue createTreatmentQueue() {
        return new Queue(CREATE_QUEUE_NAME, true);
    }

    @Bean(name = "createPacienteExchange")
    public FanoutExchange createPacienteExchange() {
        return new FanoutExchange(CREATE_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindCreateTreatmentQueue(
            @Qualifier("createTreatmentQueue") Queue createTreatmentQueue,
            @Qualifier("createPacienteExchange") FanoutExchange createPacienteExchange) {
        return BindingBuilder.bind(createTreatmentQueue).to(createPacienteExchange);
    }
}