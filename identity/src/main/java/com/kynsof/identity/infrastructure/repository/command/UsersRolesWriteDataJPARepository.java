package com.kynsof.identity.infrastructure.repository.command;

import com.kynsof.identity.infrastructure.entities.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRolesWriteDataJPARepository extends JpaRepository<UsersRoles, UUID> {

    @Modifying
    @Query("DELETE FROM UsersRoles ur WHERE ur.user.id = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}
