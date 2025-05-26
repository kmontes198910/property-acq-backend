package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDocumentResponse implements IResponse {

    private UUID id;
    private PropertyDto property;
    private String fileName;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    private Boolean ownersContractRead;
    private Boolean assignmentOfContractRead;
    private LocalDate closingDate;
    private String platMapOrSurvey;
    private String earnestMoneyDeposit;

    public PropertyDocumentResponse(PropertyDocumentDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty();
        this.fileName = dto.getFileName();
        this.filePath = dto.getFilePath();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.ownersContractRead = dto.getOwnersContractRead();
        this.assignmentOfContractRead = dto.getAssignmentOfContractRead();
        this.closingDate = dto.getClosingDate();
        this.platMapOrSurvey = dto.getPlatMapOrSurvey();
        this.earnestMoneyDeposit = dto.getEarnestMoneyDeposit();
    }

}