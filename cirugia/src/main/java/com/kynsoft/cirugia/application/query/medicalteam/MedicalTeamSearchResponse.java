package com.kynsoft.cirugia.application.query.medicalteam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalTeamSearchResponse {
    private UUID id;
    private UUID surgeryId;
    private UUID memberId;
    private String memberName;
    private String memberLastName;
    private String specialtyName;
    private String specialtyCode;
    private String role;
    private UUID businessId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    

}