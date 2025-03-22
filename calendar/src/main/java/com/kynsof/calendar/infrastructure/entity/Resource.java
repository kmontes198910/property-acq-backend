package com.kynsof.calendar.infrastructure.entity;

import com.kynsof.calendar.domain.dto.ResourceDto;
import com.kynsof.calendar.domain.dto.enumType.EResourceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Resource {

    @Id
    protected UUID id;
    @Size(max = 250)
    @NotBlank
    private String name;
    @Enumerated(EnumType.STRING)
    private EResourceStatus status;
    private String image;
    @OneToMany(mappedBy = "resource")
    private Set<ResourceService> resourceServices = new HashSet<>();
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BusinessResource> businessResources = new HashSet<>();
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Resource(ResourceDto resourceDto) {
        this.id = resourceDto.getId();
        this.name = resourceDto.getName();
        this.image = resourceDto.getImage();
        this.status = resourceDto.getStatus();
    }

    public ResourceDto toAggregate() {
        return new ResourceDto(id, name, image, status);
    }
}
