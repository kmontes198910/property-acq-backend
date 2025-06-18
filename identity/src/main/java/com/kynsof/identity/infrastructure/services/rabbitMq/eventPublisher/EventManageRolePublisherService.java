package com.kynsof.identity.infrastructure.services.rabbitMq.eventPublisher;


import com.kynsof.identity.domain.dto.ManageRolDto;

import com.kynsof.identity.infrastructure.services.rabbitMq.RabbitMQRoleConfig;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventManageRolePublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange manageRoleExchange;
    @Autowired
    public EventManageRolePublisherService(RabbitTemplate rabbitTemplate,
                                         @Qualifier("manageRoleExchange") TopicExchange manageRoleExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.manageRoleExchange = manageRoleExchange;
    }

    public void publishManageRoleEvent(ManageRolDto event) {
        rabbitTemplate.convertAndSend(
                manageRoleExchange.getName(),
                RabbitMQRoleConfig.MANAGE_ROLE_CREATED_ROUTING_KEY,
                event
        );
    }
}
