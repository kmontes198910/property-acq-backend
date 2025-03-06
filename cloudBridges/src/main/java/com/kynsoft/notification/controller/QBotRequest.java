package com.kynsoft.notification.controller;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QBotRequest {
    private String email;
    private String password;
    private String storeId;
    Map<String, String> requestData;
}