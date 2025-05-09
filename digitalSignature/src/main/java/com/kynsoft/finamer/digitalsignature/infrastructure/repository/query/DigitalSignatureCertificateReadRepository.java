package com.kynsoft.finamer.digitalsignature.infrastructure.repository.query;

import com.kynsoft.finamer.digitalsignature.infrastructure.entity.DigitalSignatureCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para operaciones de lectura de certificados de firma digital.
 */
@Repository
public interface DigitalSignatureCertificateReadRepository extends JpaRepository<DigitalSignatureCertificate, UUID>, 
                                                        JpaSpecificationExecutor<DigitalSignatureCertificate> {
    
    /**
     * Busca certificados por ID de usuario
     * @param userId ID del usuario
     * @return Lista de certificados asociados al usuario
     */
    List<DigitalSignatureCertificate> findByUserId(UUID userId);
    
    /**
     * Busca certificados por ID de negocio
     * @param businessId ID del negocio
     * @return Lista de certificados asociados al negocio
     */
    List<DigitalSignatureCertificate> findByBusinessId(UUID businessId);
    
    /**
     * Busca certificados activos
     * @return Lista de certificados activos
     */
    List<DigitalSignatureCertificate> findByIsActiveTrue();
    
    /**
     * Busca un certificado activo para un usuario específico
     * @param userId ID del usuario
     * @return Certificado activo del usuario si existe
     */
    Optional<DigitalSignatureCertificate> findByUserIdAndIsActiveTrue(UUID userId);
    
    /**
     * Busca certificados por nombre (búsqueda parcial)
     * @param certificateName Nombre parcial del certificado
     * @return Lista de certificados que contienen el nombre especificado
     */
    @Query("SELECT c FROM DigitalSignatureCertificate c WHERE LOWER(c.certificateName) LIKE LOWER(CONCAT('%', :certificateName, '%'))")
    List<DigitalSignatureCertificate> findByCertificateNameContainingIgnoreCase(@Param("certificateName") String certificateName);
}