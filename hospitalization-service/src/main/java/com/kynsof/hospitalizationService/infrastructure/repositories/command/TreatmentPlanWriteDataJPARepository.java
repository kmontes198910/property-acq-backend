package com.kynsof.hospitalizationService.infrastructure.repositories.command;

import com.kynsof.hospitalizationService.infrastructure.entity.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TreatmentPlanWriteDataJPARepository extends JpaRepository<TreatmentPlan, UUID> {

}
