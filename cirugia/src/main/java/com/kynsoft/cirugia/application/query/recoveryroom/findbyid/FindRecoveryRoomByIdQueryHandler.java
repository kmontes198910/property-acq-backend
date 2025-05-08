package com.kynsoft.cirugia.application.query.recoveryroom.findbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.cirugia.domain.dto.RecoveryRoom;
import com.kynsoft.cirugia.domain.service.IRecoveryRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindRecoveryRoomByIdQueryHandler implements IQueryHandler<FindRecoveryRoomByIdQuery, RecoveryRoomResponse> {
    
    private final IRecoveryRoomService recoveryRoomService;
    
    @Override
    public RecoveryRoomResponse handle(FindRecoveryRoomByIdQuery query) {
        log.info("Buscando sala de recuperación con ID: {}", query.getId());
        
        Optional<RecoveryRoom> recoveryRoomOptional = recoveryRoomService.findById(query.getId());
        
        if (recoveryRoomOptional.isEmpty()) {
            log.error("Sala de recuperación no encontrada con ID: {}", query.getId());
            throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "The selected schedule is not available.");
        }
        
        RecoveryRoom recoveryRoom = recoveryRoomOptional.get();
        return RecoveryRoomResponse.fromEntity(recoveryRoom);
    }
}