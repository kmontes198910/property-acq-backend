package com.kynsof.evaluation.infrastructure.repositories.command;

import com.kynsof.evaluation.infrastructure.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorWriteDataJPARepository extends JpaRepository<Doctor, UUID> {

}
