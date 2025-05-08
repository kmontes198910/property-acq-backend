package com.kynsoft.cirugia.application.query.recoveryroom.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
    private Boolean isActive;
    private String additionalInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    
    @Builder.Default
    private Set<UUID> bedIds = new HashSet<>();
}