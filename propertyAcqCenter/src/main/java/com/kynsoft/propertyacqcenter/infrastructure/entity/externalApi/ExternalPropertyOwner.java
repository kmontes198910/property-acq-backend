package com.kynsoft.propertyacqcenter.infrastructure.entity.externalApi;

import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "external_properties_owners")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExternalPropertyOwner {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "property_id")
    private ExternalProperty property;

    @ElementCollection
    @CollectionTable(
            name = "owner_names",
            joinColumns = @JoinColumn(name = "owner_id")
    )
    private List<String> names;

    private String type;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExternalPropertyMailingAddress mailingAddress;

    public ExternalPropertyOwner(UUID id, ExternalProperty property, List<String> names, String type,  PropertyResponse.MailingAddress mailingAddress) {
        this.id = id;
        this.property = property;
        this.names = names;
        this.type = type;
        this.mailingAddress = ExternalPropertyMailingAddress.builder()
                .id(UUID.randomUUID())
                .addressLine1(mailingAddress.getAddressLine1())
                .addressLine2(mailingAddress.getAddressLine2())
                .city(mailingAddress.getCity())
                .formattedAddress(mailingAddress.getFormattedAddress())
                .owner(this)
                .state(mailingAddress.getState())
                .zipCode(mailingAddress.getZipCode())
                .build();
    }

}
