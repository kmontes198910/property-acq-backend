package com.kynsoft.cirugia.application.query.recoveryroom.getbyid;

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
public class GetRecoveryRoomByIdQueryHandler implements IQueryHandler<GetRecoveryRoomByIdQuery, RecoveryRoomResponse> {

    private final IRecoveryRoomService recoveryRoomService;

    @Override
    public RecoveryRoomResponse handle(GetRecoveryRoomByIdQuery query) {
        log.info("Manejando consulta para obtener sala de recuperación con ID: {}", query.getId());

       RecoveryRoom room = recoveryRoomService.findById(query.getId());
        return RecoveryRoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .floor(room.getFloor())
                .wing(room.getWing())
                .capacity(room.getCapacity())
                .status(room.getStatus())
                .businessId(room.getBusinessId())
                .roomType(room.getRoomType())
                .isActive(room.getIsActive())
                .additionalInfo(room.getAdditionalInfo())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .createdBy(room.getCreatedBy())
                .updatedBy(room.getUpdatedBy())
                .build();
    }
}