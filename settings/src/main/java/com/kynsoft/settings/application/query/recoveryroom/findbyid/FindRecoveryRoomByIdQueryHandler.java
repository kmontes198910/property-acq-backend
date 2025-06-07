package com.kynsoft.settings.application.query.recoveryroom.findbyid;


import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.settings.domain.dto.RecoveryRoom;
import com.kynsoft.settings.domain.services.IRecoveryRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindRecoveryRoomByIdQueryHandler implements IQueryHandler<FindRecoveryRoomByIdQuery, RecoveryRoomResponse> {

    private final IRecoveryRoomService recoveryRoomService;

    @Override
    public RecoveryRoomResponse handle(FindRecoveryRoomByIdQuery query) {
        log.info("Buscando sala de recuperación con ID: {}", query.getId());

        RecoveryRoom recoveryRoom = recoveryRoomService.findById(query.getId());
        return RecoveryRoomResponse.fromEntity(recoveryRoom);
    }
}