package com.kynsoft.propertyacqcenter.application.command.teamAssignment.create;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTeamAssignmentRequest {

    private UUID buyerEntityName;
    private String buyerContactRep;
    private String titleEscrowCompany;
    private String lenderCompany;
    private String projectManager;
    private String legalContact;
    private String property;
    private String seller;
    private String hoa;
}
