package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface ContactReadDataJPARepository extends JpaRepository<Contact, UUID>, JpaSpecificationExecutor<Contact> {

    /**
     * Busca un contacto por su email
     * @param email Email a buscar
     * @return Optional con el contacto si existe
     */
    Optional<Contact> findByEmail(String email);
    
    /**
     * Busca contactos por el ID de la entidad legal asociada
     * @param legalEntityId ID de la entidad legal
     * @return Lista de contactos asociados
     */
    List<Contact> findByLegalEntityId(UUID legalEntityId);
    
    /**
     * Busca contactos por categoría
     * @param category Categoría a buscar
     * @return Lista de contactos con la categoría especificada
     */
    List<Contact> findByCategory(String category);
    
    /**
     * Busca contactos por departamento
     * @param department Departamento a buscar
     * @return Lista de contactos del departamento especificado
     */
    List<Contact> findByDepartment(String department);
    
    /**
     * Busca contactos que estén activos
     * @return Lista de contactos activos
     */
    List<Contact> findByIsActiveTrue();

    @Override
    Page<Contact> findAll(Specification<Contact> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"business", "legalEntity"})
    @Override
    Optional<Contact> findById(UUID id);
}