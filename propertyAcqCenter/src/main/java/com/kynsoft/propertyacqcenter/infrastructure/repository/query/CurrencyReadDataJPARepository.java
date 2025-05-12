package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrencyReadDataJPARepository extends JpaRepository<Currency, UUID>, JpaSpecificationExecutor<Currency> {
    @Override
    Page<Currency> findAll(Specification<Currency> specification, Pageable pageable);
    long countByCode(String code);
}