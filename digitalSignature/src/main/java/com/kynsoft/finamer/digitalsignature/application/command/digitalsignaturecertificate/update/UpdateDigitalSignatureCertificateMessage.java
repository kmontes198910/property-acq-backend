package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDigitalSignatureCertificateMessage implements ICommandMessage {
    private UUID id;
}
