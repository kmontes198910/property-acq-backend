package com.kynsoft.wamessaging.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/whatsapp/example")
public class WhatsAppMessageExampleController {

    private final WhatsAppMessageExample whatsAppMessageExample;

    @Autowired
    public WhatsAppMessageExampleController(WhatsAppMessageExample whatsAppMessageExample) {
        this.whatsAppMessageExample = whatsAppMessageExample;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage() {
        try {
            whatsAppMessageExample.sendSimpleMessage();
            return ResponseEntity.ok("Mensaje enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al enviar el mensaje: " + e.getMessage());
        }
    }
}
