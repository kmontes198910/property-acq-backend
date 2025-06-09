package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.CompanyAddressDto;
import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAddress {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    @Column(name = "street_address1", nullable = false)
    private String streetAddress1;

    @Column(name = "street_address2")
    private String streetAddress2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "nick_name")
    private String nickName;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    public CompanyAddress(CompanyAddressDto dto) {
        this.id = dto.getId();
        this.company = dto.getCompany() != null ? new Company(dto.getCompany()) : null;
        this.addressType = dto.getAddressType();
        this.streetAddress1 = dto.getStreetAddress1();
        this.streetAddress2 = dto.getStreetAddress2();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zipCode = dto.getZipCode();
        this.country = dto.getCountry();
        this.nickName = dto.getNickName();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public CompanyAddressDto toAggregateSimple() {
        return CompanyAddressDto.builder()
                .id(this.id)
                .company(company != null ? this.company.toAggregateSimple(): null)
                .addressType(addressType)
                .streetAddress1(streetAddress1)
                .streetAddress2(streetAddress2)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .country(country)
                .nickName(nickName)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }

    public CompanyAddressDto toAggregate() {
        return CompanyAddressDto.builder()
                .id(this.id)
                .company(company != null ? this.company.toAggregateBasic() : null)
                .addressType(addressType)
                .streetAddress1(streetAddress1)
                .streetAddress2(streetAddress2)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .country(country)
                .nickName(nickName)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }

}