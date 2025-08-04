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
@Table(name = "adquisition_property_hoa_building_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyHoaBuildingInfo {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "adquisition_property_id")
    private AdquisitionProperty adquisitionProperty; // Relación bidireccional

    @Column(name = "hoa_inpection_report", nullable = true)
    private String hoaInpectionReport;

    @Column(name = "hoa_electrical_report", nullable = true)
    private String hoaElectricalReport;

    @Column(name = "hoa_hvac_report", nullable = true)
    private String hoaHvacReport;

    @Column(name = "hoa_roof_report", nullable = true)
    private String hoaRoofReport;

    @Column(name = "hoa_structural_report", nullable = true)
    private String hoaStructuralReport;

    @Column(name = "hoa_plumbing_report", nullable = true)
    private String hoaPlumbingReport;

    @Column(name = "hoa_others_report", nullable = true)
    private String hoaOthersReport;

    @Column(name = "hoa_notes_report", nullable = true)
    private String hoaNotesReport;

    @Column(name = "hoa_notes", nullable = true)
    private String hoaNotes;
}
