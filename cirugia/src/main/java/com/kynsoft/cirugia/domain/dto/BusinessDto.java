package com.kynsoft.cirugia.domain.dto;

import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessDto {

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
