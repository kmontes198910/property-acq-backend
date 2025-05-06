package com.kynsoft.propertyacqcenter.infrastructure.repository.command;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyWriteDataJPARepository extends JpaRepository<Property, String> {
}
