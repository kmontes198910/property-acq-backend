package com.kynsoft.cirugia.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.dto.Surgery;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISurgeryService {
    // Métodos básicos CRUD
    UUID createSurgery(Surgery surgery);
    void updateSurgery(Surgery surgery);
    void changeSurgeryStatus(UUID surgeryId, String status, UUID updatedBy);
    void deleteSurgery(UUID id);
    
    // Métodos de consulta
    Optional<Surgery> getSurgeryById(UUID id);
    List<Surgery> listSurgeriesByBusiness(UUID businessId);
    List<Surgery> listSurgeriesByPatient(UUID patientId);
    
    // Método de búsqueda paginada
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}