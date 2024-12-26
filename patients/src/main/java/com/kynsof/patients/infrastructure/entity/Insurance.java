package com.kynsof.patients.infrastructure.entity;

import com.kynsof.patients.domain.dto.InsuranceDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Insurance {
    @Id
    private UUID id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientInsurance> patientInsurances = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Insurance(InsuranceDto insurance) {
        this.id = insurance.getId();
        this.name = insurance.getName();
        this.description = insurance.getDescription();
    }

    public InsuranceDto toAggregate() {
        return new InsuranceDto(this.id,this.name, this.description);
    }
}
