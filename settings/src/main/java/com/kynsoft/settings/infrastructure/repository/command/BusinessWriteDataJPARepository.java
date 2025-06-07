package com.kynsoft.settings.infrastructure.repository.command;

import com.kynsoft.settings.infrastructure.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessWriteDataJPARepository extends JpaRepository<Business, UUID> {
}
