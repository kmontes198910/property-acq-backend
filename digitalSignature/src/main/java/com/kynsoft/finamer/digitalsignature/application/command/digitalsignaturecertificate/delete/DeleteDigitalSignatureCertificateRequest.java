package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDigitalSignatureCertificateRequest {
    private UUID id;
}