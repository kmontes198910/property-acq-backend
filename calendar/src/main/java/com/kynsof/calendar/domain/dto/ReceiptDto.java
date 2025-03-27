package com.kynsof.calendar.domain.dto;

import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class ReceiptDto implements Serializable {

    private UUID id;
    private Double price;
    private Boolean express;
    private String reasons;
    private PatientDto user;
    private ScheduleDto schedule;
    private ServiceDto service;
    private EStatusReceipt status;
    private String requestId;
    private String authorizationCode;
    private String reference;
    private String ipAddressCreate;
    private String ipAddressPayment;
    private String userAgentCreate;
    private String userAgentPayment;
    private String consultId;
    private String processUrl;
    private String groupPaymentId;

    public ReceiptDto(UUID id, Double price, Boolean express, String reasons, PatientDto user, ScheduleDto schedule,
                      ServiceDto service, EStatusReceipt status, String requestId, String authorizationCode,
                      String reference) {
        this.id = id;
        this.price = price;
        this.express = express;
        this.reasons = reasons;
        this.user = user;
        this.schedule = schedule;
        this.service = service;
        this.status = status;
        this.requestId = requestId;
        this.authorizationCode = authorizationCode;
        this.reference = reference;
    }

    public ReceiptDto(UUID id, Double price, Boolean express, String reasons, PatientDto user, ScheduleDto schedule,
                      ServiceDto service, EStatusReceipt status) {
        this.id = id;
        this.price = price;
        this.express = express;
        this.reasons = reasons;
        this.user = user;
        this.schedule = schedule;
        this.service = service;
        this.status = status;
    }
}
