package com.kynsoft.notification.controller;

import com.kynsoft.notification.infrastructure.service.QBotService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qbot")
public class QBotController {

    private final QBotService qBotService;

    public QBotController(QBotService qBotService) {
        this.qBotService = qBotService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody QBotRequest request) {
        String accessToken = qBotService.authenticate(request.getEmail(), request.getPassword());
        return qBotService.sendMessage(accessToken, request.getStoreId(), request.requestData);
    }
}
