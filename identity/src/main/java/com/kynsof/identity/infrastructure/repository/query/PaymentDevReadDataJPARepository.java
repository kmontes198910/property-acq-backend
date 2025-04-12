package com.kynsof.identity.infrastructure.repository.query;

import com.kynsof.identity.infrastructure.entities.PaymentDev;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PaymentDevReadDataJPARepository extends JpaRepository<PaymentDev, UUID>, JpaSpecificationExecutor<PaymentDev> {

    Page<PaymentDev> findAll(Specification specification, Pageable pageable);

    @Query("SELECT COUNT(b) FROM PaymentDev b WHERE b.reference = :reference AND b.id <> :id")
    Long countByReferenceAndNotId(@Param("reference") String name, @Param("id") UUID id);

}
