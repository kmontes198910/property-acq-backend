package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import java.time.LocalDate;
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
public class InsuranceBrokerResponse implements IResponse {

    private UUID id;
    private LocalDate closingDate;
    private PropertiesBasicResponse property;
    private LegalEntityBasicResponse buyer;//Legal Entity

    private String lenderInfo;//Información de los prestamistas
    private String lenderInsurance;//Información de responsabilidad y seguro contra riesgos del prestamista

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public InsuranceBrokerResponse(InsuranceBrokerDto dto) {
        this.id = dto.getId();
        this.closingDate = dto.getClosingDate();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.buyer = dto.getBuyer() != null ? new LegalEntityBasicResponse(dto.getBuyer()) : null;
        this.lenderInfo = dto.getLenderInfo();
        this.lenderInsurance = dto.getLenderInsurance();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }

}
