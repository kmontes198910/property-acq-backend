package com.kynsoft.cirugia.application.command.recoveryroom.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.cirugia.domain.dto.RecoveryRoom;
import com.kynsoft.cirugia.domain.service.IRecoveryRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteRecoveryRoomCommandHandler implements ICommandHandler<DeleteRecoveryRoomCommand> {
    
    private final IRecoveryRoomService recoveryRoomService;
    
    @Override
    @Transactional
    public void handle(DeleteRecoveryRoomCommand command) {
        log.info("Manejando comando de eliminación de sala de recuperación con ID: {}", command.getId());
        
        // Verificar que la sala existe
        Optional<RecoveryRoom> optionalRoom = recoveryRoomService.findById(command.getId());
        if (optionalRoom.isEmpty()) {
            log.error("Sala de recuperación con ID {} no encontrada", command.getId());
            throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "The selected schedule is not available.");
        }
        
        RecoveryRoom room = optionalRoom.get();
        log.info("Eliminando sala de recuperación: ID={}, Nombre={}, Tipo={}", 
                room.getId(), room.getName(), room.getRoomType());
        
        try {
            // Intentar eliminar la sala
            recoveryRoomService.deleteById(command.getId());
            log.info("Sala de recuperación eliminada correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar la sala de recuperación: {}", e.getMessage(), e);
            throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "The selected schedule is not available.");
        }
    }
}