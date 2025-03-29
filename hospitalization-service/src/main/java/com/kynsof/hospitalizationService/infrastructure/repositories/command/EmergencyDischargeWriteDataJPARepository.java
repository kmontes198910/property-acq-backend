package com.kynsof.hospitalizationService.infrastructure.repositories.command;

import com.kynsof.hospitalizationService.infrastructure.entity.EmergencyDischarge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmergencyDischargeWriteDataJPARepository extends JpaRepository<EmergencyDischarge, UUID> {

}
