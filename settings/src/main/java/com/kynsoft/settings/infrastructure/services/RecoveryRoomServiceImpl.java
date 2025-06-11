package com.kynsoft.settings.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.settings.application.query.recoveryroom.findbyid.RecoveryRoomResponse;
import com.kynsoft.settings.domain.dto.RecoveryRoom;
import com.kynsoft.settings.domain.services.IRecoveryRoomService;
import com.kynsoft.settings.infrastructure.config.SettingsCacheConfig;
import com.kynsoft.settings.infrastructure.entity.RecoveryBedEntity;
import com.kynsoft.settings.infrastructure.entity.RecoveryRoomEntity;
import com.kynsoft.settings.infrastructure.repository.command.RecoveryBedWriteRepository;
import com.kynsoft.settings.infrastructure.repository.command.RecoveryRoomWriteRepository;
import com.kynsoft.settings.infrastructure.repository.query.RecoveryBedReadRepository;
import com.kynsoft.settings.infrastructure.repository.query.RecoveryRoomReadRepository;
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
@Transactional(readOnly = true)
public class RecoveryRoomServiceImpl implements IRecoveryRoomService {

    private final RecoveryRoomReadRepository recoveryRoomReadRepository;
    private final RecoveryRoomWriteRepository recoveryRoomWriteRepository;
    private final RecoveryBedReadRepository recoveryBedReadRepository;
    private final RecoveryBedWriteRepository recoveryBedWriteRepository;

    @Override
    @Cacheable(value = SettingsCacheConfig.RECOVERY_BED_CACHE, key = "'room_' + #id")
    public RecoveryRoom findById(UUID id) {
        log.info("Buscando sala de recuperación con ID: {}", id);
        Optional<RecoveryRoomEntity> optionalRoom = recoveryRoomReadRepository.findById(id);

        return mapToDomain(optionalRoom.get());
    }

    @Override
    @Cacheable(value = SettingsCacheConfig.RECOVERY_BED_CACHE, key = "'business_rooms_' + #businessId")
    public List<RecoveryRoom> findByBusinessId(UUID businessId) {
        log.info("Buscando salas de recuperación para el negocio con ID: {}", businessId);
        return recoveryRoomReadRepository.findByBusinessId(businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SettingsCacheConfig.RECOVERY_BED_CACHE, key = "'rooms_type_' + #type")
    public List<RecoveryRoom> findByType(String type) {
        log.info("Buscando salas de recuperación de tipo: {}", type);
        return recoveryRoomReadRepository.findByRoomType(type)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SettingsCacheConfig.RECOVERY_BED_CACHE, key = "'rooms_status_' + #status")
    public List<RecoveryRoom> findByStatus(String status) {
        log.info("Buscando salas de recuperación con estado: {}", status);
        return recoveryRoomReadRepository.findByStatus(status)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = SettingsCacheConfig.RECOVERY_BED_CACHE, allEntries = true)
    public RecoveryRoom create(RecoveryRoom recoveryRoom) {
        log.info("Creando nueva sala de recuperación: {}", recoveryRoom.getName());

        // Establecer valores iniciales
        recoveryRoom.setId(UUID.randomUUID());
        recoveryRoom.setCreatedAt(LocalDateTime.now());
        recoveryRoom.setUpdatedAt(LocalDateTime.now());

        if (recoveryRoom.getIsActive() == null) {
            recoveryRoom.setIsActive(true);
        }

        RecoveryRoomEntity entity = mapToEntity(recoveryRoom);
        RecoveryRoomEntity savedEntity = recoveryRoomWriteRepository.save(entity);

        log.info("Sala de recuperación creada correctamente con ID: {}", savedEntity.getId());
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SettingsCacheConfig.RECOVERY_BED_CACHE, allEntries = true)
    public RecoveryRoom update(RecoveryRoom recoveryRoom) {
        log.info("Actualizando sala de recuperación con ID: {}", recoveryRoom.getId());

        Optional<RecoveryRoomEntity> optionalRoom = recoveryRoomReadRepository.findById(recoveryRoom.getId());


        // Mantener valores que no deben cambiar
        recoveryRoom.setCreatedAt(optionalRoom.get().getCreatedAt());
        recoveryRoom.setUpdatedAt(LocalDateTime.now());

        RecoveryRoomEntity entity = mapToEntity(recoveryRoom);
        RecoveryRoomEntity savedEntity = recoveryRoomWriteRepository.save(entity);

        log.info("Sala de recuperación actualizada correctamente con ID: {}", savedEntity.getId());
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SettingsCacheConfig.RECOVERY_BED_CACHE, allEntries = true)
    public void updateStatus(UUID roomId, String status) {
        log.info("Actualizando estado de sala de recuperación con ID {} a {}", roomId, status);
        recoveryRoomWriteRepository.updateStatus(roomId, status);
    }

    @Override
    @Transactional
    @CacheEvict(value = SettingsCacheConfig.RECOVERY_BED_CACHE, allEntries = true)
    public void deleteById(UUID id) {
        log.info("Eliminando sala de recuperación con ID: {}", id);

        // Verificar si la sala tiene camas asignadas
        Optional<RecoveryRoomEntity> optionalRoom = recoveryRoomReadRepository.findById(id);


        RecoveryRoomEntity room = optionalRoom.get();

        if (!room.getBeds().isEmpty()) {
            log.warn("La sala tiene {} camas asignadas. Se desasociarán antes de eliminar la sala", room.getBeds().size());

            // Desasociar las camas de la sala
            for (RecoveryBedEntity bed : room.getBeds()) {
                bed.setRecoveryRoomId(null);
                recoveryBedWriteRepository.save(bed);
            }
        }

        recoveryRoomWriteRepository.deleteById(id);
        log.info("Sala de recuperación eliminada correctamente");
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Buscando salas de recuperación con criterios de filtrado: {}", filterCriteria);
        GenericSpecificationsBuilder<RecoveryRoomEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<RecoveryRoomEntity> data = recoveryRoomReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }


    private PaginatedResponse getPaginatedResponse(Page<RecoveryRoomEntity> data) {
        List<RecoveryRoomResponse> recoveryRoomResponses = new ArrayList<>();
        for (RecoveryRoomEntity entity : data.getContent()) {
            RecoveryRoom recoveryRoom = mapToDomain(entity);
            recoveryRoomResponses.add(RecoveryRoomResponse.fromEntity(recoveryRoom));
        }
        return new PaginatedResponse(
                recoveryRoomResponses,
                data.getTotalPages(),
                data.getNumberOfElements(),
                data.getTotalElements(),
                data.getSize(),
                data.getNumber()
        );
    }

    private RecoveryRoom mapToDomain(RecoveryRoomEntity entity) {

        return RecoveryRoom.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .floor(entity.getFloor())
                .wing(entity.getWing())
                .capacity(entity.getCapacity())
                .status(entity.getStatus())
                .businessId(entity.getBusinessId())
                .roomType(entity.getRoomType())
                .isActive(entity.getIsActive())
                .additionalInfo(entity.getAdditionalInfo())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    private RecoveryRoomEntity mapToEntity(RecoveryRoom dto) {
        return RecoveryRoomEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .floor(dto.getFloor())
                .wing(dto.getWing())
                .capacity(dto.getCapacity())
                .status(dto.getStatus())
                .businessId(dto.getBusinessId())
                .roomType(dto.getRoomType())
                .isActive(dto.getIsActive())
                .additionalInfo(dto.getAdditionalInfo())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }
}