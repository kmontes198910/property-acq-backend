package com.kynsoft.finamer.digitalsignature.infrastructure.repository.command;

import com.kynsoft.finamer.digitalsignature.infrastructure.entity.DigitalSignatureCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Repositorio para operaciones de escritura de certificados de firma digital.
 */
@Repository
public interface DigitalSignatureCertificateWriteRepository extends JpaRepository<DigitalSignatureCertificate, UUID> {
    
    /**
     * Actualiza el estado de activación de un certificado
     * @param id ID del certificado
     * @param isActive Estado de activación
     */
    @Modifying
    @Transactional
    @Query("UPDATE DigitalSignatureCertificate c SET c.isActive = :isActive WHERE c.id = :id")
    void updateActiveStatus(@Param("id") UUID id, @Param("isActive") Boolean isActive);
    
    /**
     * Desactiva todos los certificados de un usuario excepto el especificado
     * @param userId ID del usuario
     * @param activeId ID del certificado que debe quedar activo
     */
    @Modifying
    @Transactional
    @Query("UPDATE DigitalSignatureCertificate c SET c.isActive = false WHERE c.userId = :userId AND c.id != :activeId")
    void deactivateAllUserCertificatesExcept(@Param("userId") UUID userId, @Param("activeId") UUID activeId);
    
    /**
     * Elimina todos los certificados asociados a un usuario
     * @param userId ID del usuario
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM DigitalSignatureCertificate c WHERE c.userId = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
    
    /**
     * Elimina todos los certificados asociados a un negocio
     * @param businessId ID del negocio
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM DigitalSignatureCertificate c WHERE c.business.id = :businessId")
    void deleteByBusinessId(@Param("businessId") UUID businessId);
}