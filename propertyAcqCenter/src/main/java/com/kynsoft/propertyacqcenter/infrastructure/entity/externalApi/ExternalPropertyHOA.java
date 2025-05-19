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
@Table(name = "external_properties_hoa")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExternalPropertyHOA {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "property_id")
    private ExternalProperty property;

    private Integer fee;
}
