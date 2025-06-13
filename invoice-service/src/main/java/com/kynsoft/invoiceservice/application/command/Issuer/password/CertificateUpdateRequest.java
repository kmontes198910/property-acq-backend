package com.kynsoft.invoiceservice.application.command.Issuer.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateUpdateRequest {
    private String certificateData;
}
