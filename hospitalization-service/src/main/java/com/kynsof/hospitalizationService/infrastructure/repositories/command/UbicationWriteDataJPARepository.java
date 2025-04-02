package com.kynsof.hospitalizationService.infrastructure.repositories.command;

import com.kynsof.hospitalizationService.infrastructure.entity.Ubication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UbicationWriteDataJPARepository extends JpaRepository<Ubication, UUID> {

}
