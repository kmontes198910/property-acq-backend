package com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.MedicalTeam;
import com.kynsoft.cirugia.domain.service.IMedicalTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Manejador de consulta para obtener todos los miembros del equipo médico para una cirugía.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetMedicalTeamBySurgeryIdQueryHandler implements IQueryHandler<GetMedicalTeamBySurgeryIdQuery, MedicalTeamListResponse> {
    
    private final IMedicalTeamService medicalTeamService;

    @Override
    public MedicalTeamListResponse handle(GetMedicalTeamBySurgeryIdQuery query) {
        log.info("Obteniendo equipo médico para la cirugía con ID: {}", query.getSurgeryId());
        
        UUID surgeryId = query.getSurgeryId();
        List<MedicalTeam> medicalTeamMembers = medicalTeamService.findBySurgeryId(surgeryId);
        
        log.info("Se encontraron {} miembros del equipo médico para la cirugía con ID: {}", 
                medicalTeamMembers.size(), surgeryId);
        
        List<MedicalTeamResponse> responses = medicalTeamMembers.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return new MedicalTeamListResponse(surgeryId, responses);
    }
    
    private MedicalTeamResponse mapToResponse(MedicalTeam medicalTeam) {
        return new MedicalTeamResponse(
                medicalTeam.getId(),
                medicalTeam.getSurgeryId(),
                medicalTeam.getMemberId(),
                medicalTeam.getMemberName(),
                medicalTeam.getMemberLastName(),
                medicalTeam.getSpecialtyName(),
                medicalTeam.getSpecialtyCode(),
                medicalTeam.getSpecialityType(),
                medicalTeam.getRole(),
                medicalTeam.getCreatedAt(),
                medicalTeam.getUpdatedAt()
        );
    }
}
