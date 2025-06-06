package com.kynsoft.settings.infrastructure.repository.command;

import com.kynsoft.settings.infrastructure.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressWriteDataJPARepository extends JpaRepository<Address, UUID> {
}