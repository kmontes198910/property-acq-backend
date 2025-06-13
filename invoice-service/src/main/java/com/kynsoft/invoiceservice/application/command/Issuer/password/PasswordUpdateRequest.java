package com.kynsoft.invoiceservice.application.command.Issuer.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitudes de actualización de contraseña
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequest {
    private String password;
}
