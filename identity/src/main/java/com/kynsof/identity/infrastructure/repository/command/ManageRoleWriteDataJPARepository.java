package com.kynsof.identity.infrastructure.repository.command;

import com.kynsof.identity.infrastructure.entities.ManageRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ManageRoleWriteDataJPARepository extends JpaRepository<ManageRole, UUID> {
}
