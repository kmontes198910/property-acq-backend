package com.kynsoft.gateway.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZonedDateTime;

@Getter
@Setter
public class Status {
    private String status;
    private String reason;
    private String message;
    private String date;
}
