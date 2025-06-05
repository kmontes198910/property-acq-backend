package com.kynsoft.settings.infrastructure.entity;

import com.kynsoft.settings.domain.dto.AddressDto;
import com.kynsoft.settings.domain.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

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

    @Column(name = "nick_name", nullable = false)
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

    public Address(AddressDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.addressType = dto.getAddressType();
        this.streetAddress1 = dto.getStreetAddress1();
        this.streetAddress2 = dto.getStreetAddress2();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zipCode = dto.getZipCode();
        this.country = dto.getCountry();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.nickName = dto.getNickName();
    }

    public AddressDto toAggregate() {
        return AddressDto.builder()
                .id(this.id)
                .addressType(this.addressType)
                .streetAddress1(this.streetAddress1)
                .streetAddress2(this.streetAddress2)
                .city(this.city)
                .state(this.state)
                .zipCode(this.zipCode)
                .country(this.country)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .nickName(nickName)
                .build();
    }

    public AddressDto toAggregateSimple() {
        return AddressDto.builder()
                .id(this.id)
                .addressType(this.addressType)
                .streetAddress1(this.streetAddress1)
                .streetAddress2(this.streetAddress2)
                .city(this.city)
                .state(this.state)
                .zipCode(this.zipCode)
                .country(this.country)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .nickName(nickName)
                .build();
    }
}