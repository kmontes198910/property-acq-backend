package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;


import com.kynsoft.cirugia.domain.dto.RecoveryRoom;

import com.kynsoft.cirugia.domain.service.IRecoveryRoomService;
import com.kynsoft.cirugia.infrastructure.repository.query.RecoveryRoomReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;




@Slf4j
@Service
public class EventConsumerRecoveryRoomService {

    private final IRecoveryRoomService recoveryRoomService;

    public EventConsumerRecoveryRoomService(IRecoveryRoomService recoveryRoomService) {
        this.recoveryRoomService = recoveryRoomService;
    }

    @RabbitListener(queues = "recovery-room.queue.cirugia")
    public void handleCompanyEvent(RecoveryRoom event) {
        try {
            recoveryRoomService.findById(event.getId());
            recoveryRoomService.update(event);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                recoveryRoomService.create(event);
            } else {
                throw e;
            }
        }
    }
}