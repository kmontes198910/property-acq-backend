package com.kynsof.payment.infrastructure.repositories.query;

import com.kynsof.payment.infrastructure.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ClientReadDataJPARepository extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {
    Page<Client> findAll(Specification specification, Pageable pageable);
    Optional<Client> findByIdentification(String identification);
}
