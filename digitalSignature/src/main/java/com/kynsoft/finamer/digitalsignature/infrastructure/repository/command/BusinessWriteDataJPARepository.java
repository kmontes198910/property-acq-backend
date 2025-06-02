package com.kynsoft.finamer.digitalsignature.infrastructure.repository.command;


import com.kynsoft.finamer.digitalsignature.infrastructure.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessWriteDataJPARepository extends JpaRepository<Business, UUID> {
}
