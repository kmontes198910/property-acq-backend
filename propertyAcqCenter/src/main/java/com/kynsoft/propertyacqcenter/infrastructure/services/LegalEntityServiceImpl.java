package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.LegalEntityWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.LegalEntityReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LegalEntityServiceImpl implements ILegalEntityService {

    private final LegalEntityReadDataJPARepository repositoryQuery;
    private final LegalEntityWriteDataJPARepository repositoryCommand;

    public LegalEntityServiceImpl(LegalEntityReadDataJPARepository repositoryQuery, 
                                  LegalEntityWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(LegalEntityDto legalEntityDto) {
        LegalEntity legalEntity = new LegalEntity(legalEntityDto);
        if (legalEntity.getId() == null) {
            legalEntity.setId(UUID.randomUUID());
        }
        return repositoryCommand.save(legalEntity).getId();
    }

    @Override
    @Transactional
    public void update(LegalEntityDto legalEntityDto) {
        LegalEntity legalEntity = new LegalEntity(legalEntityDto);
        legalEntity.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(legalEntity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repositoryCommand.deleteById(id);
    }

    @Override
    public LegalEntityDto findById(UUID id) {
        Optional<LegalEntity> entity = repositoryQuery.findById(id);
        return entity.map(LegalEntity::toAggregate).orElse(null);
    }

    @Override
    public LegalEntityDto findByTaxId(String taxId) {
        Optional<LegalEntity> entity = repositoryQuery.findByTaxId(taxId);
        return entity.map(LegalEntity::toAggregate).orElse(null);
    }

    @Override
    public List<LegalEntityDto> search(Pageable pageable, List<Object> filterCriteria) {
        // Aquí se implementaría la lógica para búsqueda con criterios
        // Similar a como se hace en otros microservicios con GenericSpecificationsBuilder
        
        // Por ahora, simplemente devuelvo todas las entidades paginadas
        return repositoryQuery.findAll(pageable)
                .stream()
                .map(LegalEntity::toAggregate)
                .collect(Collectors.toList());
    }
}
