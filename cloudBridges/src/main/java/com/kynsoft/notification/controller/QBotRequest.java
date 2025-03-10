package com.kynsoft.notification.controller;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QBotRequest {
    Map<String, String> requestData;
}