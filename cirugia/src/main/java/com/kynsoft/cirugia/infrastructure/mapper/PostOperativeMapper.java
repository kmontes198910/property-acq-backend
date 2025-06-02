package com.kynsoft.cirugia.infrastructure.mapper;

import com.kynsoft.cirugia.domain.dto.PostOperative;
import com.kynsoft.cirugia.infrastructure.entities.PostOperativeEntity;
import org.springframework.stereotype.Component;

@Component
public class PostOperativeMapper {

    public PostOperative toDto(PostOperativeEntity entity) {
        if (entity == null) {
            return null;
        }
        return PostOperative.builder()
                .id(entity.getId())
                .surgeryId(entity.getSurgeryId())
                .treatmentSummary(entity.getTreatmentSummary())
                .dischargeInstructions(entity.getDischargeInstructions())
                .lifeStatus(entity.getLifeStatus())
                .dischargeCondition(entity.getDischargeCondition())
                .stayDays(entity.getStayDays())
                .restDays(entity.getRestDays())
                .clinicalSummary(entity.getClinicalSummary())
                .evolutionSummary(entity.getEvolutionSummary())
                .diagnosticFindings(entity.getDiagnosticFindings())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    public PostOperativeEntity toEntity(PostOperative dto) {
        if (dto == null) {
            return null;
        }
        return PostOperativeEntity.builder()
                .id(dto.getId())
                .surgeryId(dto.getSurgeryId())
                .treatmentSummary(dto.getTreatmentSummary())
                .dischargeInstructions(dto.getDischargeInstructions())
                .lifeStatus(dto.getLifeStatus())
                .dischargeCondition(dto.getDischargeCondition())
                .stayDays(dto.getStayDays())
                .restDays(dto.getRestDays())
                .clinicalSummary(dto.getClinicalSummary())
                .evolutionSummary(dto.getEvolutionSummary())
                .diagnosticFindings(dto.getDiagnosticFindings())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }
}