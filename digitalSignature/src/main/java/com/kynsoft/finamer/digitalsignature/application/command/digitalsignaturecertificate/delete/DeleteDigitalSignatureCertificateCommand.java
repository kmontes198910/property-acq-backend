package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDigitalSignatureCertificateCommand implements ICommand {
    private UUID id;
    private String deletedBy;
    private boolean success;
    
    public static DeleteDigitalSignatureCertificateCommand fromRequest(DeleteDigitalSignatureCertificateRequest request, String userId) {
        return DeleteDigitalSignatureCertificateCommand.builder()
                .id(request.getId())
                .deletedBy(userId)
                .build();
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new DeleteDigitalSignatureCertificateMessage(id, success);
    }
}