package com.kynsof.identity.infrastructure.entities;

import com.kynsof.identity.domain.dto.ModuleDto;
import com.kynsof.identity.domain.dto.PermissionDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "module_system")
public class ModuleSystem {

    @Id
    @Column(name = "id")
    protected UUID id;
    private String name;
    private String image;
    private String description;
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Permission> permissions = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ModuleSystem(ModuleDto module) {
        this.id = module.getId();
        this.name = module.getName();
        this.image = module.getImage();
        this.description = module.getDescription();
    }

    public ModuleDto toAggregate () {
        List<PermissionDto> p = new ArrayList<>();
        for (Permission permission : permissions) {
            p.add(new PermissionDto(permission.getId(), permission.getCode(), permission.getDescription()));
        }
        
        return new ModuleDto(id, name, image, description, p, createdAt);
    }

    public ModuleDto toAggregateSimple() {
        return new ModuleDto(id, name, image, description, createdAt);
    }
}
