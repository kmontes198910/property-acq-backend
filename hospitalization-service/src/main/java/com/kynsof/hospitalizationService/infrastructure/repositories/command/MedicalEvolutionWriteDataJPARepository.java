package com.kynsof.hospitalizationService.infrastructure.repositories.command;

import com.kynsof.hospitalizationService.infrastructure.entity.MedicalEvolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalEvolutionWriteDataJPARepository extends JpaRepository<MedicalEvolution, UUID> {

}
