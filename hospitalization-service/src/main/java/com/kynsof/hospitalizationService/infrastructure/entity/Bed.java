package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.BedDto;
import com.kynsof.hospitalizationService.domain.dto.enun.BedStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    private BedStatus status;

    @ManyToOne
    @JoinColumn(name = "ubication_id", nullable = false)
    private Ubication ubication;

    @OneToMany(mappedBy = "bed", fetch = FetchType.LAZY)
    private List<EmergencyCaseBed> emergencyCases;

    public Bed(BedDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
        this.status = dto.getStatus();
        this.ubication = dto.getUbication() != null ? new Ubication(dto.getUbication()) : null;
    }

    public BedDto toAggregate() {
        return new BedDto(id, code, name, status, ubication.toAggregate());
    }
}
