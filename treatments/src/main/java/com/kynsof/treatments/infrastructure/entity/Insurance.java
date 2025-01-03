package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.InsuranceDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Insurance {
    @Id
    @Column(name = "id")
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BusinessProcedure> businessProcedures = new HashSet<>();

    public Insurance(InsuranceDto insuranceDto) {
        this.id = insuranceDto.getId();
        this.name = insuranceDto.getName();
    }

    public InsuranceDto toAggregate() {
        return new InsuranceDto(
                id,
                name
        );
    }
}