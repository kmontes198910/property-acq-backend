package com.kynsoft.cirugia.infrastructure.services.http.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class BusinessHttp {

    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String logo;
    private String phone;
    private String email;
    private String ruc;
}
