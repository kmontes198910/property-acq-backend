package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddressResponse implements IResponse {
    private UUID id;
    private LegalEntityDto legalEntity;
    private AddressType addressType;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Boolean isPrimary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private String nickName;

    public AddressResponse(AddressDto dto) {
        this.id = dto.getId();
        this.legalEntity = dto.getLegalEntity();
        this.addressType = dto.getAddressType();
        this.streetAddress1 = dto.getStreetAddress1();
        this.streetAddress2 = dto.getStreetAddress2();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zipCode = dto.getZipCode();
        this.country = dto.getCountry();
        this.isPrimary = dto.getIsPrimary();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.nickName = dto.getNickName();
    }

}