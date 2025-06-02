package com.kynsoft.finamer.digitalsignature.infrastructure.repository.query;


import com.kynsoft.finamer.digitalsignature.infrastructure.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BusinessReadDataJPARepository extends JpaRepository<Business, UUID>, JpaSpecificationExecutor<Business> {
    @Override
    Page<Business> findAll(Specification specification, Pageable pageable);
}
