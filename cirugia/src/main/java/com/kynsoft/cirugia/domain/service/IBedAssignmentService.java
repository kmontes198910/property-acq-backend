package com.kynsoft.cirugia.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBedAssignmentService {
    Optional<BedAssignment> findById(UUID id);
    List<BedAssignment> findByPatientId(UUID patientId);
    List<BedAssignment> findBySurgeryId(UUID surgeryId);
    List<BedAssignment> findByBedId(UUID bedId);
    List<BedAssignment> findByBusinessId(UUID businessId);
    List<BedAssignment> findByStatus(String status);
    List<BedAssignment> findActiveAssignments(UUID businessId);
    BedAssignment assignBed(BedAssignment bedAssignment);
    BedAssignment update(BedAssignment bedAssignment);
    void updateStatus(UUID assignmentId, String status);
    void releaseBed(UUID assignmentId, UUID releasedBy);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
