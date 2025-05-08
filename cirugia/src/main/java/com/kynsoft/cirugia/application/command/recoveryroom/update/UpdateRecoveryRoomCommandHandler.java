package com.kynsoft.cirugia.application.command.recoveryroom.update;

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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateRecoveryRoomCommandHandler implements ICommandHandler<UpdateRecoveryRoomCommand> {
    
    private final IRecoveryRoomService recoveryRoomService;
    
    @Override
    @Transactional
    public void handle(UpdateRecoveryRoomCommand command) {
        log.info("Actualizando sala de recuperación con ID: {}", command.getRecoveryRoomId());
        
        // Obtener la sala de recuperación existente
        Optional<RecoveryRoom> roomOptional = recoveryRoomService.findById(command.getRecoveryRoomId());
        
        if (roomOptional.isEmpty()) {
            log.error("Sala de recuperación no encontrada con ID: {}", command.getRecoveryRoomId());
            throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "The selected schedule is not available.");
        }
        
        RecoveryRoom existingRoom = roomOptional.get();
        
        // Actualizar solo los campos que no son nulos en el comando
        if (command.getName() != null) {
            existingRoom.setName(command.getName());
        }
        
        if (command.getDescription() != null) {
            existingRoom.setDescription(command.getDescription());
        }
        
        if (command.getFloor() != null) {
            existingRoom.setFloor(command.getFloor());
        }
        
        if (command.getWing() != null) {
            existingRoom.setWing(command.getWing());
        }
        
        if (command.getCapacity() != null) {
            existingRoom.setCapacity(command.getCapacity());
        }
        
        if (command.getStatus() != null) {
            existingRoom.setStatus(command.getStatus());
        }
        
        if (command.getRoomType() != null) {
            existingRoom.setRoomType(command.getRoomType());
        }
        
        if (command.getAdditionalInfo() != null) {
            existingRoom.setAdditionalInfo(command.getAdditionalInfo());
        }
        
        // Actualizar campos de auditoría
        existingRoom.setUpdatedAt(LocalDateTime.now());
        existingRoom.setUpdatedBy(command.getUpdatedBy());
        
        try {
            // Guardar la sala actualizada
            recoveryRoomService.update(existingRoom);
            log.info("Sala de recuperación actualizada exitosamente: {}", existingRoom.getId());
        } catch (Exception e) {
            log.error("Error al actualizar la sala de recuperación: {}", e.getMessage());
            throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "The selected schedule is not available.");
        }
    }
}