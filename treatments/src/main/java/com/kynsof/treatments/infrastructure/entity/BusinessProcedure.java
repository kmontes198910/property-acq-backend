package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.BusinessProcedureDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "business_procedure")
@Getter
@Setter
@NoArgsConstructor
public class BusinessProcedure {
    @Id
    private UUID id;
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "business_id")
    private Business business;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;
    private Double price;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance_id", nullable = true) // Relación con Insurance
    private Insurance insurance;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public BusinessProcedure(BusinessProcedureDto businessProcedure) {
        this.id = businessProcedure.getId();
        this.business = new Business(businessProcedure.getBusiness());
        this.procedure = new Procedure(businessProcedure.getProcedure());
        this.price = businessProcedure.getPrice();
        this.code = businessProcedure.getCode();
        this.insurance = new Insurance(businessProcedure.getInsurance());
    }

    public BusinessProcedureDto toAggregate () {
        return new BusinessProcedureDto(id, business.toAggregate(), procedure.toAggregate(), price, code, insurance.toAggregate());
    }
}