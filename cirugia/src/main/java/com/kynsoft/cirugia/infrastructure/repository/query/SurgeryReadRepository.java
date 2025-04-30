package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.SurgeryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SurgeryReadRepository extends JpaRepository<SurgeryEntity, UUID>, JpaSpecificationExecutor<SurgeryEntity> {
    
    List<SurgeryEntity> findByBusinessId(UUID businessId);
    
    List<SurgeryEntity> findByPatientId(UUID patientId);
    
    List<SurgeryEntity> findByDoctorId(UUID doctorId);
    
    List<SurgeryEntity> findByStatus(String status);
    
    List<SurgeryEntity> findByScheduledDateBetweenAndBusinessId(LocalDate startDate, LocalDate endDate, UUID businessId);
    
    List<SurgeryEntity> findByScheduledDateAfterAndStatusAndBusinessId(LocalDate date, String status, UUID businessId);
}