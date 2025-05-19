package com.kynsoft.propertyacqcenter.infrastructure.entity.externalApi;

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
@Table(name = "external_properties_features")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExternalPropertyFeatures {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "property_id")
    private ExternalProperty property;

    private boolean cooling;
    private String coolingType;
    private String exteriorType;
    private int floorCount;
    private String foundationType;
    private boolean garage;
    private int garageSpaces;
    private String garageType;
    private boolean pool;
    private String poolType;
    private String roofType;
    private int unitCount;
}
