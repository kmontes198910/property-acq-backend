package com.kynsof.payment.infrastructure.repositories.command;

import com.kynsof.payment.infrastructure.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientWriteDataJPARepository extends JpaRepository<Client, UUID> {

}
