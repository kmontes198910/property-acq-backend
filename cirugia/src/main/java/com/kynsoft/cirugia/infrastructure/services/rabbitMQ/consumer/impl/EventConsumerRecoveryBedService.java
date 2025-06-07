package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;


import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;

import com.kynsoft.cirugia.infrastructure.repository.query.RecoveryBedReadRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventConsumerRecoveryBedService {

    private final IRecoveryBedService recoveryBedService;
    private final RecoveryBedReadRepository recoveryBedReadRepository;

    public EventConsumerRecoveryBedService(IRecoveryBedService recoveryBedService, RecoveryBedReadRepository recoveryBedReadRepository) {
        this.recoveryBedService = recoveryBedService;
        this.recoveryBedReadRepository = recoveryBedReadRepository;
    }

    @RabbitListener(queues = "recovery-bed.queue.cirugia")
    public void handleCompanyEvent(RecoveryBed event) {

        boolean isRecoveryBed = recoveryBedReadRepository.findById(event.getId()).isPresent();
        if(isRecoveryBed){
            this.recoveryBedService.update(event);
        }else{
            this.recoveryBedService.create(new RecoveryBed(
                    event.getId(),
                    event.getBedNumber(),
                    event.getLocation(),
                    event.getType(),
                    event.getStatus(),
                    event.getFloor(),
                    event.getRecoveryRoomId(),
                    event.getHasMonitor(),
                    event.getHasOxygenSupply(),
                    event.getLastMaintenanceDate(),
                    event.getCreatedAt(),
                    event.getUpdatedAt(),
                    event.getCreatedBy(),
                    event.getUpdatedBy(),
                    event.getBusinessId()

            ));
        }

    }
}
