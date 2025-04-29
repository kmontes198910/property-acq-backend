package com.kynsoft.cirugia.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.dto.MedicalTeam;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMedicalTeamService {
    UUID createMedicalTeam(MedicalTeam medicalTeam);
    
    void deleteMedicalTeam(UUID id);
    
    Optional<MedicalTeam> getMedicalTeamById(UUID id);
    
    List<MedicalTeam> listMedicalTeamsBySurgery(UUID surgeryId);
    
    List<MedicalTeam> listMedicalTeamsByMember(UUID memberId);
    
    List<MedicalTeam> listMedicalTeamsByBusiness(UUID businessId);
    
    List<MedicalTeam> listMedicalTeamsByRole(String role);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}