package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.BedAssignment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BedAssignmentRepository {
    BedAssignment save(BedAssignment bedAssignment);
    Optional<BedAssignment> findById(UUID id);
    List<BedAssignment> findAll();
    List<BedAssignment> findByPatientId(UUID patientId);
    List<BedAssignment> findBySurgeryId(UUID surgeryId);
    List<BedAssignment> findByBedId(UUID bedId);
    List<BedAssignment> findByStatus(String status);
    List<BedAssignment> findByBusinessId(UUID businessId);
    List<BedAssignment> findActiveAssignments(UUID businessId);
    List<BedAssignment> findByAssignmentDateBetween(LocalDateTime start, LocalDateTime end);
    void updateStatus(UUID assignmentId, String status);
    void release(UUID assignmentId, LocalDateTime releaseDate, UUID releasedBy);
}