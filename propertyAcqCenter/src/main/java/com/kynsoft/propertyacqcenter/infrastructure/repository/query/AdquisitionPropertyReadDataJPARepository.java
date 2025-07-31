package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import java.util.List;
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
public interface AdquisitionPropertyReadDataJPARepository extends JpaRepository<AdquisitionProperty, UUID>, JpaSpecificationExecutor<AdquisitionProperty> {

    @EntityGraph(attributePaths = {
        "buyer", "property", "contact", "contact.company", "adquisitionPropertyHoa", "adquisitionPropertyBuyer", 
        "adquisitionPropertyBuyerPersonalBankInfo", "adquisitionPropertyBuyerUtilitiesInfo", "buyerBankAccount", 
        "sellerBankAccount", "buyerBankAccount.contactDetails", "sellerBankAccount.contactDetails"
    })
    @Override
    Page<AdquisitionProperty> findAll(Specification<AdquisitionProperty> specification, Pageable pageable);

    @EntityGraph(attributePaths = {
        "buyer", "property", "contact", "contact.company", "adquisitionPropertyHoa", "adquisitionPropertyBuyer", 
        "adquisitionPropertyBuyerPersonalBankInfo", "adquisitionPropertyBuyerUtilitiesInfo", "buyerBankAccount", 
        "sellerBankAccount", "buyerBankAccount.contactDetails", "sellerBankAccount.contactDetails"
    })
    @Override
    Optional<AdquisitionProperty> findById(UUID id);

    @EntityGraph(attributePaths = {
        "buyer", "property", "contact", "contact.company", "adquisitionPropertyHoa", "adquisitionPropertyBuyer", 
        "adquisitionPropertyBuyerPersonalBankInfo", "adquisitionPropertyBuyerUtilitiesInfo", "buyerBankAccount", 
        "sellerBankAccount", "buyerBankAccount.contactDetails", "sellerBankAccount.contactDetails"
    })
    List<AdquisitionProperty> findByPropertyId(String propertyId);

    @Query("SELECT CASE WHEN COUNT(ap) > 0 THEN true ELSE false END "
            + "FROM AdquisitionProperty ap "
            + "WHERE ap.property.id = :propertyId")
    boolean existsByPropertyId(@Param("propertyId") String propertyId);
}
