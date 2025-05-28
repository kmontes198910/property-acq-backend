package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.AcquisitionDetails;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
@Repository
public interface AcquisitionDetailsReadDataJPARepository extends JpaRepository<AcquisitionDetails, UUID>, JpaSpecificationExecutor<AcquisitionDetails> {
    @EntityGraph(attributePaths = {"sellerName", "sellerName.parent", "sellerName.owners", "sellerContactInfo", "sellerContactInfo.parent", "sellerContactInfo.owners"})
    @Override
    Page<AcquisitionDetails> findAll(Specification<AcquisitionDetails> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"sellerName", "sellerName.parent", "sellerName.owners", "sellerContactInfo", "sellerContactInfo.parent", "sellerContactInfo.owners"})
    @Override
    Optional<AcquisitionDetails> findById(UUID id);
}