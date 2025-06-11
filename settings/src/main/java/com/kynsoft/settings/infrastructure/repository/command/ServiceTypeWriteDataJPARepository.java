package com.kynsoft.settings.infrastructure.repository.command;

import com.kynsoft.settings.infrastructure.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ServiceTypeWriteDataJPARepository extends JpaRepository<ServiceType, UUID> {
}
