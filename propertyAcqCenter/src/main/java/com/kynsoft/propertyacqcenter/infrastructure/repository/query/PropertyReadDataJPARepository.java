package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyReadDataJPARepository extends JpaRepository<Property, String>, JpaSpecificationExecutor<Property> {
    @EntityGraph(attributePaths = {"sellerName", "sellerContactInfo"})
    @Override
    Page<Property> findAll(Specification<Property> specification, Pageable pageable);

    long countById(String id);

    @EntityGraph(attributePaths = {"sellerName", "sellerContactInfo"})
    @Override
    Optional<Property> findById(String id);
}
