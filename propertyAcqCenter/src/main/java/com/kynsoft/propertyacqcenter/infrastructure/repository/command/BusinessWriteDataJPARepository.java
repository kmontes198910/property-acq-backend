package com.kynsoft.propertyacqcenter.infrastructure.repository.command;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BusinessWriteDataJPARepository extends JpaRepository<Business, UUID> {
}