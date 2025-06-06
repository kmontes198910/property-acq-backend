package com.kynsof.hospitalizationService.infrastructure.entity;


import com.kynsof.hospitalizationService.domain.dto.BusinessDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "businesses")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Business {

    @Id
    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String logo;
    private String phone;
    private String email;
    private String ruc;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Business(BusinessDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.address = dto.getAddress();
        this.logo = dto.getLogo();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
        this.ruc = dto.getRuc();
    }

    public BusinessDto toAggregate() {
        return new BusinessDto(id, name, latitude, longitude, address, logo, phone, email, ruc);
    }
}