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
    private final RecoveryRoomReadRepository recoveryRoomReadRepository;

    public EventConsumerRecoveryRoomService(IRecoveryRoomService recoveryRoomService, RecoveryRoomReadRepository recoveryRoomReadRepository) {
        this.recoveryRoomService = recoveryRoomService;
        this.recoveryRoomReadRepository = recoveryRoomReadRepository;
    }

    @RabbitListener(queues = "recovery-room.queue.cirugia")
    public void handleCompanyEvent(RecoveryRoom event) {
        boolean isRecoveryRoom = recoveryRoomReadRepository.findById(event.getId()).isPresent();
        if(isRecoveryRoom){
            this.recoveryRoomService.update(event);
        }else{
            this.recoveryRoomService.create(new RecoveryRoom(
                    event.getId(),
                    event.getName(),
                    event.getDescription(),
                    event.getFloor(),
                    event.getWing(),
                    event.getCapacity(),
                    event.getStatus(),
                    event.getBusinessId(),
                    event.getRoomType(),
                    event.getIsActive(),
                    event.getAdditionalInfo(),
                    event.getCreatedAt(),
                    event.getUpdatedAt(),
                    event.getCreatedBy(),
                    event.getUpdatedBy(),
                    event.getBedIds()

            ));
        }

    }
}
