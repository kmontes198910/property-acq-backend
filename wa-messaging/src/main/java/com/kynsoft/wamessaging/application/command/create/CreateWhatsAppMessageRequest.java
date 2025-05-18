package com.kynsoft.wamessaging.application.command.create;

import com.kynsoft.wamessaging.infrastructure.entity.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateWhatsAppMessageRequest {
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
