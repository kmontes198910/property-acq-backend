package com.kynsof.hospitalizationService.infrastructure.repositories.command;

import com.kynsof.hospitalizationService.infrastructure.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceTypeWriteDataJPARepository extends JpaRepository<ServiceType, UUID> {
}
