package com.kynsof.treatments.application.service.rabbitMQ.patientUpdate;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class RabbitMQPatientUpdateConfig {

    private static final String UPDATE_QUEUE_NAME = "paciente.update.treatment";
    private static final String UPDATE_EXCHANGE_NAME = "paciente.update.exchange";

    @Bean(name = "updateTreatmentQueue")
    public Queue updateTreatmentQueue() {
        return new Queue(UPDATE_QUEUE_NAME, true);
    }

    @Bean(name = "updatePacienteExchange")
    public FanoutExchange updatePacienteExchange() {
        return new FanoutExchange(UPDATE_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindUpdateTreatmentQueue(
            @Qualifier("updateTreatmentQueue") Queue updateTreatmentQueue,
            @Qualifier("updatePacienteExchange") FanoutExchange updatePacienteExchange) {
        return BindingBuilder.bind(updateTreatmentQueue).to(updatePacienteExchange);
    }
}