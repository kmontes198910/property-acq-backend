package com.kynsoft.cirugia.infrastructure.mapper;

import com.kynsoft.cirugia.domain.dto.AdmisionDto;
import com.kynsoft.cirugia.infrastructure.entities.AdmisionEntity;
import org.springframework.stereotype.Component;

@Component
public class AdmisionMapper {

    public AdmisionEntity toEntity(AdmisionDto dto) {
        if (dto == null) return null;

        return AdmisionEntity.builder()
                .id(dto.getId())
                .roomId(dto.getRoom())
                .bedId(dto.getBed())
                .observations(dto.getObservations())
                .surgeryId(dto.getSurgeryId())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public AdmisionDto toDto(AdmisionEntity entity) {
        if (entity == null) return null;

        return AdmisionDto.builder()
                .id(entity.getId())
                .room(entity.getRoomId())
                .bed(entity.getBedId())
                .observations(entity.getObservations())
                .surgeryId(entity.getSurgeryId())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}