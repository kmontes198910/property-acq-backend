package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty;

import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adquisition_property_bank_reference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyBankReference {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "adquisition_property_id")
    private AdquisitionProperty adquisitionProperty; // Relación bidireccional

    @Column(name = "bank_reference_name", nullable = true)
    private String bankReferenceName;

    @Column(name = "bank_reference_phone", nullable = true)
    private String bankReferencePhone;

    @Column(name = "bank_reference_email", nullable = true)
    private String bankReferenceEmail;

    @Column(name = "bank_reference_position", nullable = true)
    private String bankReferencePosition;
}
