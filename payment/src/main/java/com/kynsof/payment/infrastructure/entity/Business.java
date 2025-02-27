package com.kynsof.payment.infrastructure.entity;

import com.kynsof.payment.domain.dto.BusinessDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Business{
    @Id
    @Column(name="id")
    private UUID id;
    private String name;
    private String logo;

    public Business(BusinessDto business) {
        this.id = business.getId();
        this.name = business.getName();
        this.logo = business.getLogo();
    }

    public BusinessDto toAggregate () {
        return new BusinessDto(id, name, logo);
    }
}
