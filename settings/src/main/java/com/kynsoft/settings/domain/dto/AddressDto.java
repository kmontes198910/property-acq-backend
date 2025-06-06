package com.kynsoft.settings.domain.dto;

import com.kynsoft.settings.domain.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddressDto {
    private UUID id;
    private AddressType addressType;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private String nickName;
}