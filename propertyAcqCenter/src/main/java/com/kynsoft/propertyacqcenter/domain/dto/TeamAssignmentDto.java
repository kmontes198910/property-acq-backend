package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamAssignmentDto {
    private String buyerEntityName;
    private String buyerContactRep;
    private String titleEscrowCompany;
    private String lenderCompany;
    private String projectManager;
    private String legalContact;
    private int completionPercentage;
}
