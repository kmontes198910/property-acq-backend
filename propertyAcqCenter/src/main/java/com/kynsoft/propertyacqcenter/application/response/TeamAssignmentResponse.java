package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import java.time.LocalDateTime;
import java.util.UUID;
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
public class TeamAssignmentResponse implements IResponse {
    private UUID id;
    private String buyerEntityName;
    private String buyerContactRep;
    private String titleEscrowCompany;
    private String lenderCompany;
    private String projectManager;
    private String legalContact;
    private PropertiesBasicResponse property;
    private String seller;
    private String hoa;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public TeamAssignmentResponse(TeamAssignmentDto dto) {
        this.id = dto.getId();
        this.buyerEntityName = dto.getBuyerEntityName();
        this.buyerContactRep = dto.getBuyerContactRep();
        this.titleEscrowCompany = dto.getTitleEscrowCompany();
        this.lenderCompany = dto.getLenderCompany();
        this.projectManager = dto.getProjectManager();
        this.legalContact = dto.getLegalContact();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.hoa = dto.getHoa();
        this.seller = dto.getSeller();
    }

}
