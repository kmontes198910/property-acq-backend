package com.kynsoft.propertyacqcenter.infrastructure.entity.externalApi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Entity
@Table(name = "external_properties_mailing_addresses")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExternalPropertyMailingAddress {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private ExternalPropertyOwner owner;

    private String formattedAddress;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;

}
