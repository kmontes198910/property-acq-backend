package com.kynsoft.cirugia.application.query.recoveryroom.findbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.RecoveryRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryRoomResponse implements IResponse, Serializable {
    private UUID id;
    private String name;
    private String description;
    private String floor;
    private String wing;
    private Integer capacity;
    private String status;
    private UUID businessId;
    private String roomType;
    private String additionalInfo;
    private Boolean isActive;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
    
    public static RecoveryRoomResponse fromEntity(RecoveryRoom recoveryRoom) {
        return RecoveryRoomResponse.builder()
                .id(recoveryRoom.getId())
                .name(recoveryRoom.getName())
                .description(recoveryRoom.getDescription())
                .floor(recoveryRoom.getFloor())
                .wing(recoveryRoom.getWing())
                .capacity(recoveryRoom.getCapacity())
                .status(recoveryRoom.getStatus())
                .businessId(recoveryRoom.getBusinessId())
                .roomType(recoveryRoom.getRoomType())
                .additionalInfo(recoveryRoom.getAdditionalInfo())
                .isActive(recoveryRoom.getIsActive())
                .createdBy(recoveryRoom.getCreatedBy())
                .createdAt(recoveryRoom.getCreatedAt())
                .updatedBy(recoveryRoom.getUpdatedBy())
                .updatedAt(recoveryRoom.getUpdatedAt())
                .build();
    }
}