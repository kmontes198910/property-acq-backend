package com.kynsoft.propertyacqcenter.domain.dto.mailjet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa la respuesta del servicio de envío de correo electrónico de Mailjet
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMailjetEmailMessageDto {
    private boolean result;
}
