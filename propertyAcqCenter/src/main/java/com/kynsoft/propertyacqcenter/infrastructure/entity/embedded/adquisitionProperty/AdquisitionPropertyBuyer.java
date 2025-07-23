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
@Table(name = "adquisition_property_buyer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyBuyer {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "adquisition_property_id")
    private AdquisitionProperty adquisitionProperty; // Relación bidireccional

    @Column(name = "buyer_proof_of_funds", nullable = true)
    private String buyerProofOfFunds;

    @Column(name = "buyer_driver_license", nullable = true)
    private String buyerDriverLicense;

    @Column(name = "buyer_car_insurance", nullable = true)
    private String buyerCarInsurance;

    @Column(name = "buyer_car_brand", nullable = true)
    private String buyerCarBrand;

    @Column(name = "buyer_car_year", nullable = true)
    private Double buyerCarYear;
}
