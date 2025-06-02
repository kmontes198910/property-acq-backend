package com.kynsof.identity.infrastructure.repository.query;

import com.kynsof.identity.infrastructure.entities.FirebaseToken;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FirebaseTokenReadDataJPARepository extends JpaRepository<FirebaseToken, UUID>, JpaSpecificationExecutor<FirebaseToken> {
    Page<FirebaseToken> findAll(Specification specification, Pageable pageable);
    // Método para buscar un objeto FirebaseToken dado el id del usuario
    @Query("SELECT f FROM FirebaseToken f WHERE f.userSystem.id = :userSystemId")
    Optional<FirebaseToken> findByUserSystemId(@Param("userSystemId") UUID userSystemId);

}
