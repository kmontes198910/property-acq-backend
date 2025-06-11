package com.kynsof.hospitalizationService.infrastructure.entity;


import com.kynsof.hospitalizationService.domain.dto.ServiceTypeDto;
import com.kynsof.hospitalizationService.domain.enums.EServiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_type")
public class ServiceType implements Serializable {
    @Id
    private UUID id;

    @Size(max = 150)
    @NotBlank
    private String name;
    private String picture;

    @Column(unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private EServiceStatus status;

    @OneToMany(mappedBy = "type")
    private Set<Services> services;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ServiceType(ServiceTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.picture = dto.getPicture();
        this.status = dto.getStatus();
        this.code = dto.getCode();
    }

    public ServiceTypeDto toAggregate() {
        return new ServiceTypeDto(this.id, this.name,this.picture, status, code);
    }
}
