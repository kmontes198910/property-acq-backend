package com.kynsoft.propertyacqcenter.application.command.teamAssignment.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTeamAssignmentCommand implements ICommand {

    private UUID id;
    private String buyerEntityName;
    private String buyerContactRep;
    private String titleEscrowCompany;
    private String lenderCompany;
    private String projectManager;
    private String legalContact;
    private String property;

    public CreateTeamAssignmentCommand(String buyerEntityName, String buyerContactRep, String titleEscrowCompany, 
                                       String lenderCompany, String projectManager, String legalContact, String property) {
        this.id = UUID.randomUUID();
        this.buyerEntityName = buyerEntityName;
        this.buyerContactRep = buyerContactRep;
        this.titleEscrowCompany = titleEscrowCompany;
        this.lenderCompany = lenderCompany;
        this.projectManager = projectManager;
        this.legalContact = legalContact;
        this.property = property;
    }

    public static CreateTeamAssignmentCommand fromRequest(CreateTeamAssignmentRequest request) {
        return new CreateTeamAssignmentCommand(
                request.getBuyerEntityName(),
                request.getBuyerContactRep(),
                request.getTitleEscrowCompany(),
                request.getLenderCompany(),
                request.getProjectManager(),
                request.getLegalContact(),
                request.getProperty()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateTeamAssignmentMessage(id);
    }
}
