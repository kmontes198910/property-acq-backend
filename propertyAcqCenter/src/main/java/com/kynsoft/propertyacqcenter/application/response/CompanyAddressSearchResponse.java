package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAddressSearchResponse implements IResponse {

    private UUID id;
    private CompanyBasicResponse company;
    private AddressType addressType;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String nickName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public CompanyAddressSearchResponse(CompanyAddressDto dto) {
        this.id = dto.getId();
        this.company = new CompanyBasicResponse(dto.getCompany());
        this.addressType = dto.getAddressType();
        this.streetAddress1 = dto.getStreetAddress1();
        this.streetAddress2 = dto.getStreetAddress2();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zipCode = dto.getZipCode();
        this.country = dto.getCountry();
        this.nickName = dto.getNickName();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

}