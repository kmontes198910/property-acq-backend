package com.kynsoft.cirugia.infrastructure.services;

import com.kynsoft.cirugia.domain.dto.Evolution;
import com.kynsoft.cirugia.domain.service.IEvolutionRepository;
import com.kynsoft.cirugia.infrastructure.entities.EvolutionEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.EvolutionWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.EvolutionReadRepository;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de evoluciones médicas.
 */
@Repository
@Transactional
@Slf4j
public class EvolutionRepository implements IEvolutionRepository {

    private final EvolutionWriteRepository evolutionWriteRepository;
    private final EvolutionReadRepository evolutionReadRepository;

    public EvolutionRepository(EvolutionWriteRepository evolutionWriteRepository, EvolutionReadRepository evolutionReadRepository) {
        this.evolutionWriteRepository = evolutionWriteRepository;
        this.evolutionReadRepository = evolutionReadRepository;
    }

    @Override
    public Evolution create(Evolution evolution) {
        log.info("Creando nueva evolución para la cirugía ID: {}", evolution.getSurgeryId());
        
        if (evolution.getId() == null) {
            evolution.setId(UUID.randomUUID());
            evolution.setCreatedAt(LocalDateTime.now());
        }
        evolution.setUpdatedAt(LocalDateTime.now());
        
        EvolutionEntity entity = mapToEntity(evolution);
        evolutionWriteRepository.save(entity);
        evolutionWriteRepository.flush();
        return mapToDto(entity);
    }

    @Override
    public Optional<Evolution> findById(String id) {
        try {
            log.info("Buscando evolución con ID: {}", id);
            Optional<EvolutionEntity> entity = evolutionReadRepository.findById(UUID.fromString(id));
            return entity.map(this::mapToDto);
        } catch (Exception e) {
            log.error("Error al buscar evolución con ID {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Evolution> findBySurgeryId(UUID surgeryId) {
        try {
            log.info("Buscando evoluciones para la cirugía ID: {}", surgeryId);
            List<EvolutionEntity> entities = evolutionReadRepository.findBySurgeryId(surgeryId);
            return entities.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        } catch (NoResultException e) {
            log.warn("No se encontraron evoluciones para la cirugía ID: {}", surgeryId);
            return List.of();
        }
    }

    @Override
    public List<Evolution> findBySurgeryIdOrderByDateDesc(UUID surgeryId) {
        try {
            log.info("Buscando evoluciones ordenadas por fecha para la cirugía ID: {}", surgeryId);
            List<EvolutionEntity> entities = evolutionReadRepository.findBySurgeryIdOrderByEvolutionDateDesc(surgeryId);
            return entities.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        } catch (NoResultException e) {
            log.warn("No se encontraron evoluciones para la cirugía ID: {}", surgeryId);
            return List.of();
        }
    }

    @Override
    public List<Evolution> findByEvolutionDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            log.info("Buscando evoluciones entre {} y {}", startDate, endDate);
            List<EvolutionEntity> entities = evolutionReadRepository.findByEvolutionDateBetween(startDate, endDate);
            return entities.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        } catch (NoResultException e) {
            log.warn("No se encontraron evoluciones en el rango de fechas especificado");
            return List.of();
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            log.info("Eliminando evolución con ID: {}", id);
            Optional<EvolutionEntity> entity = evolutionWriteRepository.findById(UUID.fromString(id));
            if (entity.isPresent()) {
                evolutionWriteRepository.delete(entity.get());
                log.info("Evolución eliminada correctamente");
            } else {
                log.warn("No se pudo eliminar la evolución porque no se encontró con ID: {}", id);
            }
        } catch (Exception e) {
            log.error("Error al eliminar evolución con ID {}: {}", id, e.getMessage());
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Realizando búsqueda paginada de evoluciones con filtros: {}", filterCriteria);
        GenericSpecificationsBuilder<EvolutionEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<EvolutionEntity> data = evolutionReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<EvolutionEntity> data) {
        List<Evolution> evolutions = new ArrayList<>();
        for (EvolutionEntity entity : data.getContent()) {
            evolutions.add(mapToDto(entity));
        }
        return new PaginatedResponse(
            evolutions,
            data.getTotalPages(),
            data.getNumberOfElements(),
            data.getTotalElements(),
            data.getSize(),
            data.getNumber()
        );
    }

    private EvolutionEntity mapToEntity(Evolution dto) {
        return EvolutionEntity.builder()
                .id(dto.getId())
                .surgeryId(dto.getSurgeryId())
                .therapeuticFluids(dto.getTherapeuticFluids())
                .prescriptionFluids(dto.getPrescriptionFluids())
                .generalCare(dto.getGeneralCare())
                .monitoring(dto.getMonitoring())
                .diet(dto.getDiet())
                .analytics(dto.getAnalytics())
                .others(dto.getOthers())
                .evolutionDate(dto.getEvolutionDate())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }

    private Evolution mapToDto(EvolutionEntity entity) {
        return Evolution.builder()
                .id(entity.getId())
                .surgeryId(entity.getSurgeryId())
                .therapeuticFluids(entity.getTherapeuticFluids())
                .prescriptionFluids(entity.getPrescriptionFluids())
                .generalCare(entity.getGeneralCare())
                .monitoring(entity.getMonitoring())
                .diet(entity.getDiet())
                .analytics(entity.getAnalytics())
                .others(entity.getOthers())
                .evolutionDate(entity.getEvolutionDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}