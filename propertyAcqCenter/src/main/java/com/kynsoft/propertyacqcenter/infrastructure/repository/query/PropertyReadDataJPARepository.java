package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.domain.dto.projection.PropertyWithProfileDTO;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyReadDataJPARepository extends JpaRepository<Property, String>, JpaSpecificationExecutor<Property> {

    @EntityGraph(attributePaths = {"sellerName", "sellerContactInfo", "buyerName"})
    @Override
    Page<Property> findAll(Specification<Property> specification, Pageable pageable);

    long countById(String id);

    @EntityGraph(attributePaths = {
        "sellerName", "sellerContactInfo", "buyerName", "propertyTeams", "propertyTeams.property",
        "propertyTeams.contact"
    })
    @Override
    Optional<Property> findById(String id);

    @EntityGraph(attributePaths = {
        "sellerName", "sellerContactInfo", "buyerName", "propertyTeams", "propertyTeams.property",
        "propertyTeams.contact"
    })
    @Query("SELECT NEW com.kynsoft.propertyacqcenter.domain.dto.projection.PropertyWithProfileDTO(p, pt.profile) "
            + "FROM Property p JOIN p.propertyTeams pt "
            + "WHERE pt.contact.id = :contactId")
    List<PropertyWithProfileDTO> findPropertiesWithProfileByContact(@Param("contactId") UUID contactId);
}
