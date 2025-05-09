package com.kynsoft.finamer.digitalsignature.infrastructure.entity;

import com.kynsoft.finamer.digitalsignature.domain.dto.BusinessDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "businesses")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Business {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Business(BusinessDto businessDto) {
        this.id = businessDto.getId();
        this.name = businessDto.getName();
    }

    public BusinessDto toAggregate() {
        return BusinessDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}