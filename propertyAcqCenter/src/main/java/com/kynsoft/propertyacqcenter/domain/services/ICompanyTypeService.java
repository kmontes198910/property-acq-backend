package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz para el servicio de tipos de empresas inmobiliarias
 */
public interface ICompanyTypeService {
    
    /**
     * Crea un nuevo tipo de empresa
     * @param companyTypeDto datos del tipo de empresa a crear
     * @return UUID del tipo de empresa creado
     */
    UUID create(CompanyTypeDto companyTypeDto);
    
    /**
     * Actualiza un tipo de empresa existente
     * @param companyTypeDto datos del tipo de empresa a actualizar
     */
    void update(CompanyTypeDto companyTypeDto);
    
    /**
     * Elimina un tipo de empresa por su ID
     * @param id UUID del tipo de empresa a eliminar
     */
    void delete(UUID id);
    
    /**
     * Busca un tipo de empresa por su ID
     * @param id UUID del tipo de empresa a buscar
     * @return CompanyTypeDto si se encuentra, null en caso contrario
     */
    CompanyTypeDto findById(UUID id);
    
    /**
     * Busca un tipo de empresa por su código
     * @param code Código del tipo de empresa a buscar
     * @return CompanyTypeDto si se encuentra, null en caso contrario
     */
    CompanyTypeDto findByCode(String code);
    
    /**
     * Busca tipos de empresas con criterios de filtrado y paginación
     * @param pageable configuración de paginación
     * @param filterCriteria criterios de filtrado
     * @return Lista de CompanyTypeDto que coinciden con los criterios
     */
    List<CompanyTypeDto> search(Pageable pageable, List<Object> filterCriteria);
    
    /**
     * Obtiene todos los tipos de empresas activos
     * @return Lista de CompanyTypeDto activos
     */
    List<CompanyTypeDto> findAllActive();
}