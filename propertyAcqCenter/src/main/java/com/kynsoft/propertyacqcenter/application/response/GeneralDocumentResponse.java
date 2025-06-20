package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralDocumentResponse implements IResponse {

    private UUID id;
    private DocumentTypeResponse documentType;
    private String fileName;
    private String filePath;

    public GeneralDocumentResponse(GeneralDocumentDto dto) {
        this.id = dto.getId();
        this.documentType = dto.getDocumentType() != null ? new DocumentTypeResponse(dto.getDocumentType()) : null;
        this.fileName = dto.getFileName();
        this.filePath = dto.getFilePath();
    }

}