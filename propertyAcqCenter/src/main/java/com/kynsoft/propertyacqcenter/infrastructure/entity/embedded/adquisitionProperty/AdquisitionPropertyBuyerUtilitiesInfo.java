package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty;

import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adquisition_property_buyer_utilities_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyBuyerUtilitiesInfo {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "adquisition_property_id")
    private AdquisitionProperty adquisitionProperty; // Relación bidireccional

    @Column(name = "buyer_electric_provider_account", nullable = true)
    private String buyerElectricProviderAccount;

    @Column(name = "buyer_gas_service_account", nullable = true)
    private String buyerGasServiceAccount;

    @Column(name = "buyer_trash_service_account", nullable = true)
    private String buyerTrashServiceAccount;

    @Column(name = "buyer_water_sewer_setup_account", nullable = true)
    private String buyerWaterSewerSetupAccount;

    @Column(name = "buyer_internet_service", nullable = true)
    private String buyerInternetService;

    @Column(name = "buyer_notes", nullable = true)
    private String buyerNotes;

    @Column(name = "buyer_start_service_date", nullable = true)
    private LocalDate buyerStartServiceDate;

    @Column(name = "buyer_deposit_amount", nullable = true)
    private Double buyerDepositAmount;

    @Column(name = "gas_buyer_start_service_date", nullable = true)
    private LocalDate gasBuyerStartServiceDate;

    @Column(name = "gas_buyer_deposit_amount", nullable = true)
    private Double gasBuyerDepositAmount;

    @Column(name = "trash_buyer_start_service_date", nullable = true)
    private LocalDate trashBuyerStartServiceDate;

    @Column(name = "trash_buyer_deposit_amount", nullable = true)
    private Double trashBuyerDepositAmount;

    @Column(name = "water_buyer_start_service_date", nullable = true)
    private LocalDate waterBuyerStartServiceDate;

    @Column(name = "water_buyer_deposit_amount", nullable = true)
    private Double waterBuyerDepositAmount;

    @Column(name = "internet_buyer_start_service_date", nullable = true)
    private LocalDate internetBuyerStartServiceDate;

    @Column(name = "internet_buyer_deposit_amount", nullable = true)
    private Double internetBuyerDepositAmount;
}
