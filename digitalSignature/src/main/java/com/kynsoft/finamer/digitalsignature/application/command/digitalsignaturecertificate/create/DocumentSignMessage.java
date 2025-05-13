package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSignMessage implements ICommandMessage {
    private byte[] signedDocument;
}