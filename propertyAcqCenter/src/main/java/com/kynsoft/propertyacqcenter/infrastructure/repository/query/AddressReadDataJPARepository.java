package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Address;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AddressReadDataJPARepository extends JpaRepository<Address, UUID>, JpaSpecificationExecutor<Address> {
    @Override
    Page<Address> findAll(Specification<Address> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"legalEntity.business", "legalEntity", "legalEntity.owners"})
    @Override
    Optional<Address> findById(UUID id);

    @Query("SELECT COUNT(a) FROM Address a WHERE a.legalEntity.id = :legalEntity AND a.addressType = :addressType AND a.id <> :id")
    int countByLegalEntityAndIsPrimary(@Param("legalEntity") UUID legalEntity, @Param("addressType") AddressType addressType, @Param("id") UUID id);
}