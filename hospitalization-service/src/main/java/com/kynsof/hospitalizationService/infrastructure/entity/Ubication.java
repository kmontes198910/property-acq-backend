package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.UbicationDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "ubication")
public class Ubication {
    @Id
    @Column(name="id")
    private UUID id;
    private String code;
    private String name;

    public Ubication(UbicationDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
    }

    public UbicationDto toAggregate() {
        return new UbicationDto(id, code, name);
    }
}
