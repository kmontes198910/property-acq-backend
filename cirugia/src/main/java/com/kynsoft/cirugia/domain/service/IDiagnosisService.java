package com.kynsoft.cirugia.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.dto.Diagnosis;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDiagnosisService {
    // Métodos básicos CRUD
    Diagnosis create(Diagnosis diagnosis);
    Diagnosis update(Diagnosis diagnosis);
    void delete(UUID id);
    
    // Métodos de consulta
    Optional<Diagnosis> findById(UUID id);
    List<Diagnosis> findBySurgeryId(UUID surgeryId);
    
    // Método de búsqueda paginada
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}