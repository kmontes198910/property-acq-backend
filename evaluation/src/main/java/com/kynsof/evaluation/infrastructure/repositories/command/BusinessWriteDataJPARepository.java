package com.kynsof.evaluation.infrastructure.repositories.command;

import com.kynsof.evaluation.infrastructure.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessWriteDataJPARepository extends JpaRepository<Business, UUID> {
}
