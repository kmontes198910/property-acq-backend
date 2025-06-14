package com.kynsoft.propertyacqcenter.application.command.teamAssignment.update;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTeamAssignmentRequest {

    private UUID buyerEntityName;
    private String property;
    private List<UUID> buyerContactRep;
    private List<UUID> titleEscrowCompany;
    private List<UUID> lenderCompany;
    private List<UUID> projectManager;
    private List<UUID> legalContact;
    private List<UUID> seller;
    private List<UUID> hoa;
}
