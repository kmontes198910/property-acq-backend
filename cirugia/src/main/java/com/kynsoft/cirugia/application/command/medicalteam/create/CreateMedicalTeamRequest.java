package com.kynsoft.cirugia.application.command.medicalteam.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMedicalTeamRequest {
    private UUID surgeryId;
    private UUID memberId;
    private String memberName;
    private String memberLastName;
    private String specialtyName;
    private String specialtyCode;
    private String role;
    private UUID businessId;
}