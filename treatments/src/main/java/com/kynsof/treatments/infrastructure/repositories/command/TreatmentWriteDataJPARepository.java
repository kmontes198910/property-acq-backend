package com.kynsof.treatments.infrastructure.repositories.command;

import com.kynsof.treatments.infrastructure.entity.Treatment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TreatmentWriteDataJPARepository extends JpaRepository<Treatment, UUID> {
    @Modifying
    @Transactional
    @Query(value = "delete from treatment where id = :id", nativeQuery = true)
    void deleteByCustomIdNative(@Param("id") UUID id);

}
