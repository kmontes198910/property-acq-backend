package com.kynsoft.cirugia.application.query.medicalteam;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalTeamSearchResponse implements IResponse, Serializable {
    private UUID id;
    private UUID surgeryId;
    private UUID memberId;
    private String memberName;
    private String memberLastName;
    private String specialtyName;
    private String specialtyCode;
    private String specialityType;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    

}