package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CompanyAddressReadDataJPARepository extends JpaRepository<CompanyAddress, UUID>, JpaSpecificationExecutor<CompanyAddress> {
    @EntityGraph(attributePaths = {"company"})
    @Override
    Page<CompanyAddress> findAll(Specification<CompanyAddress> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"company", "company.companyType", "company.business", "company.subCompanyType", "company.subCategory"})
    @Override
    Optional<CompanyAddress> findById(UUID id);

    @Query("SELECT COUNT(a) FROM CompanyAddress a WHERE a.company.id = :company AND a.addressType = :addressType AND a.id <> :id")
    int countByCompanyAddressAndIsPrimary(@Param("company") UUID company, @Param("addressType") AddressType addressType, @Param("id") UUID id);

}