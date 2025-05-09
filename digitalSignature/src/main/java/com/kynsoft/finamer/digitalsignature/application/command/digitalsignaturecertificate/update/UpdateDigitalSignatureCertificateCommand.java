package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update;

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
public class UpdateDigitalSignatureCertificateCommand implements ICommand {
    private UUID id;
    private String certificateName;
    private byte[] certificateP12;
    private String certificatePassword;
    private LocalDateTime expirationDate;
    private Boolean isActive;
    private UUID businessId;
    private String updatedBy;
    
    public static UpdateDigitalSignatureCertificateCommand fromRequest(UpdateDigitalSignatureCertificateRequest request, UUID id, String userId) {
        return UpdateDigitalSignatureCertificateCommand.builder()
                .id(id)
                .certificateName(request.getCertificateName())
                .certificateP12(request.getCertificateP12Base64() != null ? 
                        Base64.getDecoder().decode(request.getCertificateP12Base64()) : null)
                .certificatePassword(request.getCertificatePassword())
                .expirationDate(request.getExpirationDate())
                .isActive(request.getIsActive())
                .businessId(request.getBusinessId())
                .updatedBy(userId)
                .build();
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateDigitalSignatureCertificateMessage(id);
    }
}
