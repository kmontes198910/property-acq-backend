package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.BedDto;
import jakarta.persistence.Column;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bed")
public class Bed {
    @Id
    @Column(name="id")
    private UUID id;
    private String code;
    private String name;

    @ManyToOne
    @JoinColumn(name = "ubication_id", nullable = false)
    private Ubication ubication;

    public Bed(BedDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
        this.ubication = dto.getUbication() != null ? new Ubication(dto.getUbication()) : null;
    }

    public BedDto toAggregate() {
        return new BedDto(id, code, name, ubication.toAggregate());
    }
}
