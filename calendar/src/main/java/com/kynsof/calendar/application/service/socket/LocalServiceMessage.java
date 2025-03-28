package com.kynsof.calendar.application.service.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalServiceMessage {
    // local id that receives the service
    private String queueId;

    private String shiftId;
    private String shift;
    private String service;

    private String identification;
    private boolean preferential;
    private String status;
}
