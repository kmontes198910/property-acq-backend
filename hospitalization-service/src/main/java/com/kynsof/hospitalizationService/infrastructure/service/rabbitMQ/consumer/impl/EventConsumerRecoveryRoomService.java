package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer.impl;


import com.kynsof.hospitalizationService.domain.dto.RecoveryRoom;
import com.kynsof.hospitalizationService.domain.service.IRecoveryRoomService;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.RecoveryRoomReadRepository;
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

    @RabbitListener(queues = "recovery-room.queue.hospitalization")
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
