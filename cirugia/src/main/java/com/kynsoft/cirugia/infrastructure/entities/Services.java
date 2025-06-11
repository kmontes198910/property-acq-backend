package com.kynsoft.cirugia.infrastructure.entities;


import com.kynsoft.cirugia.domain.dto.ServiceDto;
import com.kynsoft.cirugia.domain.enums.EServiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Services {
    @Id
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "service_type_id") // Asume una columna foreign key service_type_id
    private ServiceType type;

    @Enumerated(EnumType.STRING)
    private EServiceStatus status;

    @Column(unique = true)
    private String code;
    private String picture;
    private String name;
    private Double normalAppointmentPrice;
    @Column(nullable = true)
    private Integer estimatedDuration;

    @Size(max = 2000)
    private String description;


    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean applyIva = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //@OneToMany(mappedBy = "services", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private Set<BusinessServices> businessServices = new HashSet<>();




    public Services(ServiceDto object) {
        this.id = object.getId();
        this.type = new ServiceType(object.getType());
        this.status = object.getStatus();
        this.picture = object.getPicture();
        this.name = object.getName();
        this.normalAppointmentPrice = object.getNormalAppointmentPrice();
        this.description = object.getDescription();
        this.applyIva = object.getApplyIva();
        this.estimatedDuration = object.getEstimatedDuration();
        this.code = object.getCode();

    }

    public ServiceDto toAggregate () {
        return new ServiceDto(id, type.toAggregate(), status, picture, name, normalAppointmentPrice,
                description, applyIva, estimatedDuration, code);
    }
}
