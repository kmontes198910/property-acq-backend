package com.kynsoft.finamer.digitalsignature.application.query.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.finamer.digitalsignature.domain.dto.DigitalSignatureCertificateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalSignatureCertificateResponse implements IResponse {
    private UUID id;

    private UUID userId;

    private String certificateName;

    private String certificateP12Base64;

    private String certificatePassword;

    private LocalDateTime expirationDate;

    private Boolean isActive;

    private Boolean isPrimaryKey;

    private UUID businessId;

    private String businessName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public DigitalSignatureCertificateResponse(DigitalSignatureCertificateDto certificateDto) {
        this.id = certificateDto.getId();
        this.userId = certificateDto.getUserId();
        this.certificateName = certificateDto.getCertificateName();
        this.certificateP12Base64 = certificateDto.getCertificateP12Base64();
        this.certificatePassword = certificateDto.getCertificatePassword();
        this.expirationDate = certificateDto.getExpirationDate();
        this.isActive = certificateDto.getIsActive();
        this.isPrimaryKey = certificateDto.getIsPrimaryKey();
        this.businessId = certificateDto.getBusinessId();
        this.businessName = certificateDto.getBusinessName();
        this.createdAt = certificateDto.getCreatedAt();
        this.updatedAt = certificateDto.getUpdatedAt();
    }
}