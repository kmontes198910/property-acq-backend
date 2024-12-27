package com.kynsof.calendar.application.query;

import com.kynsof.calendar.application.query.service.ServicesResponse;
import com.kynsof.calendar.domain.dto.PatientDto;
import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ReceiptResponse implements IResponse {
    private UUID id;
    private Double price;
    private Boolean express;
    private String reasons;
    private String requestId;
    private PatientDto user;
    private ScheduleResponse schedule;
    private ServicesResponse service;
    private EStatusReceipt status;
    private String consultId;
    private String processUrl;


    public ReceiptResponse(ReceiptDto object) {
        this.id = object.getId();
        this.price = object.getPrice();
        this.express = object.getExpress();
        this.requestId = object.getRequestId();
        this.reasons = object.getReasons();
        this.user = object.getUser();
        this.schedule = new ScheduleResponse(object.getSchedule());
        this.service = new ServicesResponse(object.getService());
        this.status = object.getStatus();
        this.consultId = object.getConsultId();
        this.processUrl = object.getProcessUrl();
    }

}