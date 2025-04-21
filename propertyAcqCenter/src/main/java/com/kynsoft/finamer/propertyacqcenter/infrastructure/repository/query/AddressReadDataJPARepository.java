package com.kynsoft.finamer.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.Address;
import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.enums.AddressType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressReadDataJPARepository extends JpaRepository<Address, UUID>, JpaSpecificationExecutor<Address> {
    Page<Address> findAll(Specification<Address> specification, Pageable pageable);
    
    List<Address> findByLegalEntityId(UUID legalEntityId);
    
    @Query("SELECT a FROM Address a WHERE a.legalEntity.id = :legalEntityId AND a.isPrimary = true")
    Optional<Address> findPrimaryAddressByLegalEntityId(@Param("legalEntityId") UUID legalEntityId);
    
    @Query("SELECT a FROM Address a WHERE a.legalEntity.id = :legalEntityId AND a.addressType = :addressType")
    List<Address> findByLegalEntityIdAndAddressType(
            @Param("legalEntityId") UUID legalEntityId, 
            @Param("addressType") AddressType addressType);
}