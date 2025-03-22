package com.kynsof.patients.infrastructure.repository.query;

import com.kynsof.patients.infrastructure.entity.FirebaseToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FirebaseTokenReadDataJPARepository extends JpaRepository<FirebaseToken, UUID>, JpaSpecificationExecutor<FirebaseToken> {
    Page<FirebaseToken> findAll(Specification specification, Pageable pageable);
}
