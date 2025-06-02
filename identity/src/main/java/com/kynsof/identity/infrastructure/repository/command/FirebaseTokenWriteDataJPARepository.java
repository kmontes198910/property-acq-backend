package com.kynsof.identity.infrastructure.repository.command;

import com.kynsof.identity.infrastructure.entities.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FirebaseTokenWriteDataJPARepository extends JpaRepository<FirebaseToken, UUID> {
}
