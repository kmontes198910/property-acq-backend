package com.kynsoft.settings.infrastructure.repository.command;

import com.kynsoft.settings.infrastructure.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ServiceWriteDataJPARepository extends JpaRepository<Services, UUID> {
}
