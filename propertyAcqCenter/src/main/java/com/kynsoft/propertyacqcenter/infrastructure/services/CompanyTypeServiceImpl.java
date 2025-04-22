package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyType;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.CompanyTypeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.CompanyTypeReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyTypeServiceImpl implements ICompanyTypeService {

    private final CompanyTypeReadDataJPARepository repositoryQuery;
    private final CompanyTypeWriteDataJPARepository repositoryCommand;

    public CompanyTypeServiceImpl(
            CompanyTypeReadDataJPARepository repositoryQuery,
            CompanyTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(CompanyTypeDto companyTypeDto) {
        // Asignar un nuevo UUID si no tiene uno
        if (companyTypeDto.getId() == null) {
            companyTypeDto.setId(UUID.randomUUID());
        }
        
        // Establecer valores por defecto para campos no inicializados
        if (companyTypeDto.getIsActive() == null) {
            companyTypeDto.setIsActive(true);
        }
        
        // Convertir DTO a entidad
        CompanyType companyType = new CompanyType(companyTypeDto);
        
        // Guardar la entidad y devolver su ID
        companyType = repositoryCommand.save(companyType);
        return companyType.getId();
    }

    @Override
    @Transactional
    public void update(CompanyTypeDto companyTypeDto) {
        // Verificar que exista el tipo de empresa
        Optional<CompanyType> existingCompanyType = repositoryQuery.findById(companyTypeDto.getId());
        if (existingCompanyType.isPresent()) {
            CompanyType companyType = existingCompanyType.get();
            
            // Actualizar propiedades del tipo de empresa
            companyType.setName(companyTypeDto.getName());
            companyType.setCode(companyTypeDto.getCode());
            companyType.setDescription(companyTypeDto.getDescription());
            companyType.setExamples(companyTypeDto.getExamples());
            companyType.setIsActive(companyTypeDto.getIsActive());
            
            // Guardar los cambios
            repositoryCommand.save(companyType);
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repositoryCommand.deleteById(id);
    }

    @Override
    public CompanyTypeDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(CompanyType::toAggregate)
                .orElse(null);
    }

    @Override
    public CompanyTypeDto findByCode(String code) {
        return repositoryQuery.findByCode(code)
                .map(CompanyType::toAggregate)
                .orElse(null);
    }

    @Override
    public List<CompanyTypeDto> search(Pageable pageable, List<Object> filterCriteria) {
        // Implementación genérica que devuelve todos los tipos de empresas con paginación
        // Se puede mejorar añadiendo filtros específicos según los criterios
        return repositoryQuery.findAll(pageable)
                .stream()
                .map(CompanyType::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyTypeDto> findAllActive() {
        return repositoryQuery.findByIsActiveTrue()
                .stream()
                .map(CompanyType::toAggregate)
                .collect(Collectors.toList());
    }
}