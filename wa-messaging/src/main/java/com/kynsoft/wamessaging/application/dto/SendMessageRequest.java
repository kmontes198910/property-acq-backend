package com.kynsoft.wamessaging.application.dto;

import com.kynsoft.wamessaging.infrastructure.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para la creación de mensajes de WhatsApp
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

    @NotBlank(message = "El número de teléfono del destinatario es obligatorio")
    @Pattern(regexp = "^\\d{12}$", message = "El número debe tener 12 dígitos (código país + número sin espacios ni símbolos)")
    private String recipientPhone;

    private String recipientName;

    @NotNull(message = "El contenido del mensaje es obligatorio")
    private Object messageContent;

    @NotNull(message = "El tipo de mensaje es obligatorio")
    private MessageType messageType;

    private String templateName;
}

