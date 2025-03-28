package com.kynsof.calendar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleServiceInfoDto {
    private UUID businessId;
    private String businessName;
    private String businessAddress;
    private String businessLogo;
    private Double servicePrice;
    private String latitude;
    private String longitude;
    private String phone;
    private String email;
    private String ruc;
}
