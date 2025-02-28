package com.kynsof.payment.infrastructure.repositories.command;

import com.kynsof.payment.infrastructure.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingWriteDataJPARepository extends JpaRepository<Billing, UUID> {

}
