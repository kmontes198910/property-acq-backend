package com.kynsoft.propertyacqcenter.infrastructure.entity.externalApi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "external_properties_history")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExternalPropertyHistory {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private ExternalProperty property;

    private String historyKey;
    private String event;
    private String date;
    private Integer price;
}
