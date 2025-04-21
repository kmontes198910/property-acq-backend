package com.kynsoft.finamer.propertyacqcenter.infrastructure.repository.command;

import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressWriteDataJPARepository extends JpaRepository<Address, UUID> {
}