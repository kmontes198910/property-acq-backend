package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.application.query.operatingroom.OperatingRoomSearchResponse;
import com.kynsoft.cirugia.domain.service.IOperatingRoomService;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.entities.OperatingRoomEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.OperatingRoomWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.OperatingRoomReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperatingRoomServiceImpl implements IOperatingRoomService {

    private final OperatingRoomWriteRepository operatingRoomWriteRepository;
    private final OperatingRoomReadRepository operatingRoomReadRepository;

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public UUID createOperatingRoom(OperatingRoom operatingRoom) {
        log.info("Creating operating room: {}", operatingRoom.getRoomNumber());
        
        if (operatingRoom.getId() == null) {
            operatingRoom.setId(UUID.randomUUID());
        }
        
        operatingRoom.setCreatedAt(LocalDateTime.now());
        operatingRoom.setUpdatedAt(LocalDateTime.now());
        
        OperatingRoomEntity entity = mapToEntity(operatingRoom);
        entity = operatingRoomWriteRepository.save(entity);
        
        return entity.getId();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public void updateOperatingRoom(OperatingRoom operatingRoom) {
        log.info("Updating operating room with ID: {}", operatingRoom.getId());
        
        if (operatingRoom.getId() == null) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, "Operating Room ID cannot be null");
        }
        
        OperatingRoomEntity entity = operatingRoomReadRepository.findById(operatingRoom.getId())
                .orElseThrow(() -> new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                    new ErrorField("id", "Operating Room not found with ID: " + operatingRoom.getId()))));

        // Actualizar campos desde operatingRoom
        entity.setRoomNumber(operatingRoom.getRoomNumber());
        entity.setName(operatingRoom.getName());
        entity.setLocation(operatingRoom.getLocation());
        entity.setFloor(operatingRoom.getFloor());
        entity.setWing(operatingRoom.getWing());
        entity.setType(operatingRoom.getType());
        entity.setSize(operatingRoom.getSize());
        entity.setCapacity(operatingRoom.getCapacity());
        entity.setHasVentilationSystem(operatingRoom.getHasVentilationSystem());
        entity.setHasAnesthesiaEquipment(operatingRoom.getHasAnesthesiaEquipment());
        entity.setHasSurgicalLights(operatingRoom.getHasSurgicalLights());
        entity.setHasMonitoringSystems(operatingRoom.getHasMonitoringSystems());
        entity.setHasOxygenSupply(operatingRoom.getHasOxygenSupply());
        entity.setHasXRayCapability(operatingRoom.getHasXRayCapability());
        entity.setHasLaserEquipment(operatingRoom.getHasLaserEquipment());
        entity.setHasRoboticsSystem(operatingRoom.getHasRoboticsSystem());
        entity.setSpecialFeatures(operatingRoom.getSpecialFeatures());
        entity.setStatus(operatingRoom.getStatus());
        entity.setLastMaintenanceDate(operatingRoom.getLastMaintenanceDate());
        entity.setNextMaintenanceDate(operatingRoom.getNextMaintenanceDate());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(operatingRoom.getUpdatedBy());
        
        operatingRoomWriteRepository.save(entity);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public void updateStatus(UUID roomId, String status, UUID updatedBy) {
        log.info("Changing operating room {} status to: {}", roomId, status);
        
        OperatingRoomEntity entity = operatingRoomReadRepository.findById(roomId)
                .orElseThrow(() -> new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                    new ErrorField("id", "Operating Room not found with ID: " + roomId))));
        
        entity.setStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(updatedBy);
        
        operatingRoomWriteRepository.save(entity);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public void deleteOperatingRoom(UUID id) {
        log.info("Deleting operating room with ID: {}", id);
        
        if (!operatingRoomReadRepository.existsById(id)) {
            log.warn("Operating Room not found with ID: {}", id);
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Operating Room not found with ID: " + id)));
        }
        
        try {
            operatingRoomWriteRepository.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException(DomainErrorMessage.NOT_DELETE, 
                "Operating Room cannot be deleted as it has related elements: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, key = "#id", unless = "#result == null")
    public Optional<OperatingRoom> getOperatingRoomById(UUID id) {
        log.info("Finding operating room with ID: {}", id);
        return operatingRoomReadRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'operatingRooms:businessId:' + #businessId", 
              unless = "#result == null")
    public List<OperatingRoom> listOperatingRoomsByBusiness(UUID businessId) {
        log.info("Listing operating rooms for business: {}", businessId);
        return operatingRoomReadRepository.findByBusinessId(businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'operatingRooms:status:' + #status", 
              unless = "#result == null")
    public List<OperatingRoom> listOperatingRoomsByStatus(String status) {
        log.info("Listing operating rooms with status: {}", status);
        return operatingRoomReadRepository.findByStatus(status)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'operatingRooms:type:' + #type", 
              unless = "#result == null")
    public List<OperatingRoom> listOperatingRoomsByType(String type) {
        log.info("Listing operating rooms of type: {}", type);
        return operatingRoomReadRepository.findByType(type)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE,
              key = "'operatingRooms:available:' + #businessId",
              unless = "#result == null")
    public List<OperatingRoom> listAvailableOperatingRooms(UUID businessId) {
        log.info("Listing available operating rooms for business: {}", businessId);
        return operatingRoomReadRepository.findAvailableRooms(businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE,
              key = "'operatingRooms:floor:' + #floor + ':business:' + #businessId",
              unless = "#result == null")
    public List<OperatingRoom> listOperatingRoomsByFloor(String floor, UUID businessId) {
        log.info("Listing operating rooms on floor '{}' for business: {}", floor, businessId);
        return operatingRoomReadRepository.findByFloorAndBusinessId(floor, businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE,
              key = "'operatingRooms:wing:' + #wing + ':business:' + #businessId",
              unless = "#result == null")
    public List<OperatingRoom> listOperatingRoomsByWing(String wing, UUID businessId) {
        log.info("Listing operating rooms in wing '{}' for business: {}", wing, businessId);
        return operatingRoomReadRepository.findByWingAndBusinessId(wing, businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE,
            key = "'operatingRooms:search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching operating rooms with filters: {}", filterCriteria);
        
        GenericSpecificationsBuilder<OperatingRoomEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<OperatingRoomEntity> data = operatingRoomReadRepository.findAll(specifications, pageable);
        
        return getPaginatedResponse(data);
    }
    
    private PaginatedResponse getPaginatedResponse(Page<OperatingRoomEntity> data) {
        List<OperatingRoomSearchResponse> operatingRoomResponses = new ArrayList<>();
        for (OperatingRoomEntity entity : data.getContent()) {
            operatingRoomResponses.add(new OperatingRoomSearchResponse(entity));
        }
        return new PaginatedResponse(
            operatingRoomResponses, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }
    
    private OperatingRoom mapToDomain(OperatingRoomEntity entity) {
        return OperatingRoom.builder()
                .id(entity.getId())
                .roomNumber(entity.getRoomNumber())
                .name(entity.getName())
                .location(entity.getLocation())
                .floor(entity.getFloor())
                .wing(entity.getWing())
                .type(entity.getType())
                .size(entity.getSize())
                .capacity(entity.getCapacity())
                .hasVentilationSystem(entity.getHasVentilationSystem())
                .hasAnesthesiaEquipment(entity.getHasAnesthesiaEquipment())
                .hasSurgicalLights(entity.getHasSurgicalLights())
                .hasMonitoringSystems(entity.getHasMonitoringSystems())
                .hasOxygenSupply(entity.getHasOxygenSupply())
                .hasXRayCapability(entity.getHasXRayCapability())
                .hasLaserEquipment(entity.getHasLaserEquipment())
                .hasRoboticsSystem(entity.getHasRoboticsSystem())
                .specialFeatures(entity.getSpecialFeatures())
                .status(entity.getStatus())
                .lastMaintenanceDate(entity.getLastMaintenanceDate())
                .nextMaintenanceDate(entity.getNextMaintenanceDate())
                .businessId(entity.getBusinessId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
    
    private OperatingRoomEntity mapToEntity(OperatingRoom operatingRoom) {
        return OperatingRoomEntity.builder()
                .id(operatingRoom.getId())
                .roomNumber(operatingRoom.getRoomNumber())
                .name(operatingRoom.getName())
                .location(operatingRoom.getLocation())
                .floor(operatingRoom.getFloor())
                .wing(operatingRoom.getWing())
                .type(operatingRoom.getType())
                .size(operatingRoom.getSize())
                .capacity(operatingRoom.getCapacity())
                .hasVentilationSystem(operatingRoom.getHasVentilationSystem())
                .hasAnesthesiaEquipment(operatingRoom.getHasAnesthesiaEquipment())
                .hasSurgicalLights(operatingRoom.getHasSurgicalLights())
                .hasMonitoringSystems(operatingRoom.getHasMonitoringSystems())
                .hasOxygenSupply(operatingRoom.getHasOxygenSupply())
                .hasXRayCapability(operatingRoom.getHasXRayCapability())
                .hasLaserEquipment(operatingRoom.getHasLaserEquipment())
                .hasRoboticsSystem(operatingRoom.getHasRoboticsSystem())
                .specialFeatures(operatingRoom.getSpecialFeatures())
                .status(operatingRoom.getStatus())
                .lastMaintenanceDate(operatingRoom.getLastMaintenanceDate())
                .nextMaintenanceDate(operatingRoom.getNextMaintenanceDate())
                .businessId(operatingRoom.getBusinessId())
                .createdAt(operatingRoom.getCreatedAt())
                .updatedAt(operatingRoom.getUpdatedAt())
                .createdBy(operatingRoom.getCreatedBy())
                .updatedBy(operatingRoom.getUpdatedBy())
                .build();
    }
}