package com.kynsof.calendar.application.service.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBillingPartialRequest {

    private String code;
    private String description;
    private Double cost;

}
