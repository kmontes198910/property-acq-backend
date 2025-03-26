package com.kynsof.calendar.infrastructure.entity;

import com.kynsof.calendar.domain.dto.BusinessServicesDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "business_services")
@Getter
@Setter
@NoArgsConstructor
public class BusinessServices implements Serializable {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Services services;

    private Double price;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public BusinessServices(BusinessServicesDto businessServices) {
        this.id = businessServices.getId();
        this.business = new Business(businessServices.getBusiness());
        this.services = new Services(businessServices.getService());
        this.price = businessServices.getPrice();
    }

    public BusinessServicesDto toAggregate () {
        return new BusinessServicesDto(id, business.toAggregate(), services.toAggregate(), price, createdAt);
    }
}