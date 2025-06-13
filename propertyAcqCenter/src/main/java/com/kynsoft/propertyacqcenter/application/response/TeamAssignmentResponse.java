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
    private LegalEntityBasicResponse buyerEntityName;
    private CompanyContactSearchResponse buyerContactRep;
    private CompanyContactSearchResponse titleEscrowCompany;
    private CompanyContactSearchResponse lenderCompany;
    private CompanyContactSearchResponse projectManager;
    private CompanyContactSearchResponse legalContact;
    private PropertiesBasicResponse property;
    private CompanyContactSearchResponse seller;
    private CompanyContactSearchResponse hoa;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public TeamAssignmentResponse(TeamAssignmentDto dto) {
        this.id = dto.getId();
        this.buyerEntityName = dto.getBuyerEntityName() != null ? new LegalEntityBasicResponse(dto.getBuyerEntityName()) : null;
        this.buyerContactRep = dto.getBuyerContactRep() != null ? new CompanyContactSearchResponse(dto.getBuyerContactRep()) : null;
        this.titleEscrowCompany = dto.getTitleEscrowCompany() != null ? new CompanyContactSearchResponse(dto.getTitleEscrowCompany()) : null;
        this.lenderCompany = dto.getLenderCompany() != null ? new CompanyContactSearchResponse(dto.getLenderCompany()) : null;
        this.projectManager = dto.getProjectManager() != null ? new CompanyContactSearchResponse(dto.getProjectManager()) : null;
        this.legalContact = dto.getLegalContact() != null ? new CompanyContactSearchResponse(dto.getLegalContact()) : null;
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.hoa = dto.getHoa() != null ? new CompanyContactSearchResponse(dto.getHoa()) : null;
        this.seller = dto.getSeller() != null ? new CompanyContactSearchResponse(dto.getSeller()) : null;
    }

}
