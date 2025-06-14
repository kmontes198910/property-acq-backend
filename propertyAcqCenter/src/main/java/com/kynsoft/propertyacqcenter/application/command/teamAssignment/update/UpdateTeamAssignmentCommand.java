package com.kynsoft.propertyacqcenter.application.command.teamAssignment.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateTeamAssignmentCommand implements ICommand {

    private UUID id;
    private UUID buyerEntityName;
    private String property;
    private List<UUID> buyerContactRep;
    private List<UUID> titleEscrowCompany;
    private List<UUID> lenderCompany;
    private List<UUID> projectManager;
    private List<UUID> legalContact;
    private List<UUID> seller;
    private List<UUID> hoa;

    public UpdateTeamAssignmentCommand(UUID id, UUID buyerEntityName, String property, List<UUID> buyerContactRep, List<UUID> titleEscrowCompany, 
                                       List<UUID> lenderCompany, List<UUID> projectManager, List<UUID> legalContact, 
                                       List<UUID> seller, List<UUID> hoa) {
        this.id = id;
        this.buyerEntityName = buyerEntityName;
        this.buyerContactRep = buyerContactRep;
        this.titleEscrowCompany = titleEscrowCompany;
        this.lenderCompany = lenderCompany;
        this.projectManager = projectManager;
        this.legalContact = legalContact;
        this.property = property;
        this.seller = seller;
        this.hoa = hoa;
    }

    public static UpdateTeamAssignmentCommand fromRequest(UpdateTeamAssignmentRequest request, UUID id) {
        return new UpdateTeamAssignmentCommand(
                id,
                request.getBuyerEntityName(),
                request.getProperty(),
                request.getBuyerContactRep(),
                request.getTitleEscrowCompany(),
                request.getLenderCompany(),
                request.getProjectManager(),
                request.getLegalContact(),
                
                request.getSeller(),
                request.getHoa()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateTeamAssignmentMessage(id);
    }
}
