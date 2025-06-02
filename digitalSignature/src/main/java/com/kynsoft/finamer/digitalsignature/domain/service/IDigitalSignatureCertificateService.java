package com.kynsoft.finamer.digitalsignature.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.finamer.digitalsignature.infrastructure.entity.DigitalSignatureCertificate;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDigitalSignatureCertificateService {

    /**
     * Crea un nuevo certificado de firma digital
     * 
     * @param certificate Certificado a crear
     * @param businessId ID del negocio asociado
     * @param createdBy Usuario que crea el certificado
     * @return Certificado creado
     */
    DigitalSignatureCertificate create(DigitalSignatureCertificate certificate);
    
    /**
     * Actualiza un certificado de firma digital existente
     * 
     * @param certificate Certificado con los datos actualizados
     * @param businessId ID del negocio (opcional, si se va a cambiar)
     * @param updatedBy Usuario que actualiza el certificado
     * @return Certificado actualizado
     */
    DigitalSignatureCertificate update(DigitalSignatureCertificate certificate, UUID businessId, String updatedBy);
    
    /**
     * Elimina un certificado de firma digital
     * 
     * @param id ID del certificado a eliminar
     * @param deletedBy Usuario que elimina el certificado
     * @return true si se eliminó correctamente, false si no se encontró
     */
    boolean delete(UUID id, String deletedBy);
    
    /**
     * Busca un certificado por su ID
     * 
     * @param id ID del certificado
     * @return Certificado encontrado (Optional)
     */
    Optional<DigitalSignatureCertificate> findById(UUID id);
    
    /**
     * Busca certificados con paginación y filtros
     * 
     * @param pageable Configuración de paginación
     * @param filters Filtros a aplicar
     * @param query Consulta de texto
     * @return Respuesta paginada
     */
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filters, String query);

}