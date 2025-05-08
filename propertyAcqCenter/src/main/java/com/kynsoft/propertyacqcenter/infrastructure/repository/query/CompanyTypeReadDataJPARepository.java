package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyTypeReadDataJPARepository extends JpaRepository<CompanyType, UUID>, JpaSpecificationExecutor<CompanyType> {

    /**
     * Busca un tipo de empresa por su código
     * @param code Código a buscar
     * @return Optional con el tipo de empresa si existe
     */
    Optional<CompanyType> findByCode(String code);

    long countByCode(String code);
    
    /**
     * Busca tipos de empresas por su nombre o parte del nombre
     * @param name Nombre o parte del nombre a buscar
     * @return Lista de tipos de empresas que coinciden
     */
    List<CompanyType> findByNameContainingIgnoreCase(String name);
    
    /**
     * Busca tipos de empresas activos
     * @return Lista de tipos de empresas activos
     */
    List<CompanyType> findByIsActiveTrue();
}