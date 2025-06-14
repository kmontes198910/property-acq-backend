package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
    private List<CompanyContactSearchResponse> buyerContactRep;
    private List<CompanyContactSearchResponse> titleEscrowCompany;
    private List<CompanyContactSearchResponse> lenderCompany;
    private List<CompanyContactSearchResponse> projectManager;
    private List<CompanyContactSearchResponse> legalContact;
    private PropertiesBasicResponse property;
    private List<CompanyContactSearchResponse> seller;
    private List<CompanyContactSearchResponse> hoa;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public TeamAssignmentResponse(TeamAssignmentDto dto) {
        this.id = dto.getId();
        this.buyerEntityName = dto.getBuyerEntityName() != null ? new LegalEntityBasicResponse(dto.getBuyerEntityName()) : null;
        this.buyerContactRep = get(dto.getBuyerContactReps());
        this.titleEscrowCompany = get(dto.getTitleEscrowCompany());
        this.lenderCompany = get(dto.getLenderCompany());
        this.projectManager = get(dto.getProjectManager());
        this.legalContact = get(dto.getLegalContact());
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.hoa = get(dto.getHoa());
        this.seller = get(dto.getSeller());
    }

    private List<CompanyContactSearchResponse> get(List<CompanyContactDto> ids) {
        return ids.stream()
                .map(CompanyContactSearchResponse::new)
                .collect(Collectors.toList());
    }

}
