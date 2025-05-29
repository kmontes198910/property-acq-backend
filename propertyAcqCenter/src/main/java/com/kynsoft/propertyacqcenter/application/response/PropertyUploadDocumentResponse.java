package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyUploadDocumentResponse implements IResponse {

    private UUID id;
    private String fileName;
    private String document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PropertiesBasicResponse property;

    public PropertyUploadDocumentResponse(PropertyUploadDocumentDto dto) {
        this.id = dto.getId();
        this.fileName = dto.getFileName();
        this.document = dto.getDocument();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
    }

}
