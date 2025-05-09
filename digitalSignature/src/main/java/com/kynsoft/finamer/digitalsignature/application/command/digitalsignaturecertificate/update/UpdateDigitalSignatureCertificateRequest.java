package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDigitalSignatureCertificateRequest {
    private String certificateName;
    private String certificateP12Base64; // El certificado en formato Base64
    private String certificatePassword;
    private LocalDateTime expirationDate;
    private Boolean isActive;
    private UUID businessId;
}