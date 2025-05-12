package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDigitalSignatureCertificateCommand implements ICommand {
    private UUID id;
    private UUID userId;
    private String certificateName;
    private byte[] certificateP12;
    private String certificatePassword;
    private LocalDateTime expirationDate;
    private Boolean isPrimaryKey;
    private UUID businessId;
    private String createdBy;
    
    public static CreateDigitalSignatureCertificateCommand fromRequest(CreateDigitalSignatureCertificateRequest request, String userId) {
        return CreateDigitalSignatureCertificateCommand.builder()
                .id(UUID.randomUUID())
                .userId(request.getUserId())
                .certificateName(request.getCertificateName())
                .certificateP12(request.getCertificateP12Base64() != null ? 
                        Base64.getDecoder().decode(request.getCertificateP12Base64()) : null)
                .certificatePassword(request.getCertificatePassword())
                .expirationDate(request.getExpirationDate())
                .isPrimaryKey(request.getIsPrimaryKey())
                .businessId(request.getBusinessId())
                .createdBy(userId)
                .build();
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CreateDigitalSignatureCertificateMessage(id);
    }
}