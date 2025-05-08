package com.kynsoft.cirugia.application.command.recoveryroom.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.cirugia.domain.dto.RecoveryRoom;
import com.kynsoft.cirugia.domain.service.IRecoveryRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateRecoveryRoomCommandHandler implements ICommandHandler<CreateRecoveryRoomCommand> {
    
    private final IRecoveryRoomService recoveryRoomService;
    
    @Override
    @Transactional
    public void handle(CreateRecoveryRoomCommand command) {
        log.info("Manejando comando de creación de sala de recuperación: {}", command.getName());
        
        try {
            RecoveryRoom recoveryRoom = RecoveryRoom.builder()
                    .name(command.getName())
                    .description(command.getDescription())
                    .floor(command.getFloor())
                    .wing(command.getWing())
                    .capacity(command.getCapacity())
                    .status(command.getStatus() != null ? command.getStatus() : "AVAILABLE")
                    .businessId(command.getBusinessId())
                    .roomType(command.getRoomType())
                    .additionalInfo(command.getAdditionalInfo())
                    .isActive(true)
                    .createdBy(command.getCreatedBy())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            RecoveryRoom createdRoom = recoveryRoomService.create(recoveryRoom);
            log.info("Sala de recuperación creada con ID: {}", createdRoom.getId());
            
            // Importante: Establecer el ID de la sala creada en el comando para que el mensaje tenga el ID correcto
            command.setId(createdRoom.getId());
        } catch (Exception e) {
            log.error("Error al crear la sala de recuperación: {}", e.getMessage());
            throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "The selected schedule is not available.");
        }
    }
}