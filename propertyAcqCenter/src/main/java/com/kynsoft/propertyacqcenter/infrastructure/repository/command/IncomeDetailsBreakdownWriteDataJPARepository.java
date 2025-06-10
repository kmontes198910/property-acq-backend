package com.kynsoft.propertyacqcenter.infrastructure.repository.command;

import com.kynsoft.propertyacqcenter.infrastructure.entity.IncomeDetailsBreakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IncomeDetailsBreakdownWriteDataJPARepository extends JpaRepository<IncomeDetailsBreakdown, UUID> {
}