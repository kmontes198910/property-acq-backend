package com.kynsoft.invoiceservice.application.command.Issuer.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitudes de verificación de contraseña
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordVerificationRequest {
    private String password;
}
