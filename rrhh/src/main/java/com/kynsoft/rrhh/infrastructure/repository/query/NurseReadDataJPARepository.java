package com.kynsoft.rrhh.infrastructure.repository.query;

import com.kynsoft.rrhh.infrastructure.identity.Nurse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NurseReadDataJPARepository extends JpaRepository<Nurse, UUID>, JpaSpecificationExecutor<Nurse> {
    Optional<Nurse> findByIdentification(String identification);
    
    Page<Nurse> findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndIdentificationContainingIgnoreCaseAndCodeContainingIgnoreCase(
            String name, String lastName, String email, String identification, String code, Pageable pageable);
    
    @Query("SELECT COUNT(n) FROM Nurse n WHERE n.identification = :identification")
    Long countByIdentification(@Param("identification") String identification);
    
    @Query("SELECT COUNT(n) FROM Nurse n WHERE n.email = :email")
    Long countByEmail(@Param("email") String email);
    
    @Query("SELECT COUNT(n) FROM Nurse n WHERE n.identification = :identification AND n.id <> :id")
    Long countByIdentificationAndNotId(@Param("identification") String identification, @Param("id") UUID id);
    
    @Query("SELECT COUNT(n) FROM Nurse n WHERE n.email = :email AND n.id <> :id")
    Long countByEmailAndNotId(@Param("email") String email, @Param("id") UUID id);
    
    @Query("SELECT COUNT(n) FROM Nurse n WHERE n.code = :code AND n.id <> :id")
    Long countByCodeAndNotId(@Param("code") String code, @Param("id") UUID id);
}