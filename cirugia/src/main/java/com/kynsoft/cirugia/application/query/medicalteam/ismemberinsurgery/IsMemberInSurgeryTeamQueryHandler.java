package com.kynsoft.cirugia.application.query.medicalteam.ismemberinsurgery;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.service.IMedicalTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador de consulta para verificar si un miembro específico forma parte del equipo médico de una cirugía.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class IsMemberInSurgeryTeamQueryHandler implements IQueryHandler<IsMemberInSurgeryTeamQuery, IsMemberInSurgeryTeamResponse> {
    
    private final IMedicalTeamService medicalTeamService;
    
    @Override
    public IsMemberInSurgeryTeamResponse handle(IsMemberInSurgeryTeamQuery query) {
        log.info("Verificando si el miembro con ID: {} forma parte del equipo médico para la cirugía con ID: {}", 
                query.getMemberId(), query.getSurgeryId());
        
        boolean isMemberInTeam = medicalTeamService.isMemberInSurgeryTeam(query.getSurgeryId(), query.getMemberId());
        
        log.info("El miembro con ID: {} {} forma parte del equipo médico para la cirugía con ID: {}", 
                query.getMemberId(), isMemberInTeam ? "sí" : "no", query.getSurgeryId());
        
        return new IsMemberInSurgeryTeamResponse(isMemberInTeam);
    }
}
