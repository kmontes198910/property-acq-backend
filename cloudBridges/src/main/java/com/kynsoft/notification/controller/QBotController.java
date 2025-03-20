package com.kynsoft.notification.controller;

import com.kynsoft.notification.infrastructure.service.QBotService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qbot")
public class QBotController {

    private final QBotService qBotService;

    public QBotController(QBotService qBotService) {
        this.qBotService = qBotService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody QBotRequest request) {
        String accessToken = qBotService.authenticate();
        return qBotService.sendMessage(accessToken, request.requestData);
    }
}
