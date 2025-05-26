package com.kynsoft.propertyacqcenter.application.command.teamAssignment.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTeamAssignmentRequest {

    private String buyerEntityName;
    private String buyerContactRep;
    private String titleEscrowCompany;
    private String lenderCompany;
    private String projectManager;
    private String legalContact;
    private String property;
}
