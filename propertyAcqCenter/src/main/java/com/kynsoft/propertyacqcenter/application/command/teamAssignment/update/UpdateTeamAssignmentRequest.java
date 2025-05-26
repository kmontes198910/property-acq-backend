package com.kynsoft.propertyacqcenter.application.command.teamAssignment.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTeamAssignmentRequest {

    private String buyerEntityName;
    private String buyerContactRep;
    private String titleEscrowCompany;
    private String lenderCompany;
    private String projectManager;
    private String legalContact;
    private String property;
}
