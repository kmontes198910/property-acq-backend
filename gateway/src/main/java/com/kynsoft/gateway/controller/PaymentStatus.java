package com.kynsoft.gateway.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentStatus {
    private Status status;
    private int requestId;
    private String reference;
    private String signature;
}
