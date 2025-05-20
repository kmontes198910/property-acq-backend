package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.domain.service.IVitalSignsService;
import com.kynsoft.cirugia.domain.dto.VitalSigns;
import com.kynsoft.cirugia.domain.util.BMIClassification;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.entities.VitalSignsEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.VitalSignsWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.VitalSignsReadRepository;
import com.kynsoft.cirugia.application.query.vitalsigns.getbyid.VitalSignsResponse;
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
public class VitalSignsServiceImpl implements IVitalSignsService {

    private final VitalSignsReadRepository vitalSignsReadRepository;
    private final VitalSignsWriteRepository vitalSignsWriteRepository;

    @Override
    @Cacheable(value = SurgeryCacheConfig.VITAL_SIGNS_CACHE, key = "#id")
    public Optional<VitalSigns> findById(UUID id) {
        log.info("Finding vital signs with ID: {}", id);
        return vitalSignsReadRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.VITAL_SIGNS_CACHE, key = "'patient_' + #patientId")
    public List<VitalSigns> findByPatientId(UUID patientId) {
        log.info("Finding vital signs for patient ID: {}", patientId);
        return vitalSignsReadRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.VITAL_SIGNS_CACHE, key = "'surgery_' + #surgeryId")
    public List<VitalSigns> findBySurgeryId(UUID surgeryId) {
        log.info("Finding vital signs for surgery ID: {}", surgeryId);
        return vitalSignsReadRepository.findBySurgeryId(surgeryId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.VITAL_SIGNS_CACHE, key = "'latest_patient_' + #patientId")
    public List<VitalSigns> findLatestByPatientId(UUID patientId) {
        log.info("Finding latest vital signs for patient ID: {}", patientId);
        return vitalSignsReadRepository.findLatestByPatientId(patientId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.VITAL_SIGNS_CACHE, key = "'latest_surgery_' + #surgeryId")
    public List<VitalSigns> findLatestBySurgeryId(UUID surgeryId) {
        log.info("Finding latest vital signs for surgery ID: {}", surgeryId);
        return vitalSignsReadRepository.findLatestBySurgeryId(surgeryId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.VITAL_SIGNS_CACHE, allEntries = true)
    public VitalSigns create(VitalSigns vitalSigns) {
        log.info("Creating new vital signs record for patient ID: {}", vitalSigns.getPatientId());
        
        vitalSigns.setId(UUID.randomUUID());
        vitalSigns.setCreatedAt(LocalDateTime.now());
        vitalSigns.setUpdatedAt(LocalDateTime.now());
        
        if (vitalSigns.getRecordedAt() == null) {
            vitalSigns.setRecordedAt(LocalDateTime.now());
        }
        
        // Calcular el IMC y su clasificación
        calculateAndSetBMI(vitalSigns);
        
        VitalSignsEntity entity = mapToEntity(vitalSigns);
        VitalSignsEntity savedEntity = vitalSignsWriteRepository.save(entity);
        
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.VITAL_SIGNS_CACHE, allEntries = true)
    public VitalSigns update(VitalSigns vitalSigns) {
        log.info("Updating vital signs with ID: {}", vitalSigns.getId());
        
        vitalSigns.setUpdatedAt(LocalDateTime.now());
        
        // Calcular el IMC y su clasificación
        calculateAndSetBMI(vitalSigns);
        
        VitalSignsEntity entity = mapToEntity(vitalSigns);
        VitalSignsEntity savedEntity = vitalSignsWriteRepository.save(entity);
        
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.VITAL_SIGNS_CACHE, allEntries = true)
    public void delete(UUID id) {
        log.info("Deleting vital signs with ID: {}", id);
        vitalSignsWriteRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching vital signs with filter criteria: {}", filterCriteria);
        GenericSpecificationsBuilder<VitalSignsEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<VitalSignsEntity> data = vitalSignsReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<VitalSignsEntity> data) {
        List<VitalSignsResponse> vitalSignsResponses = new ArrayList<>();
        for (VitalSignsEntity entity : data.getContent()) {
            // Convertir a objeto de dominio y luego a response
            VitalSigns vitalSigns = mapToDomain(entity);
            vitalSignsResponses.add(new VitalSignsResponse(vitalSigns));
        }
        return new PaginatedResponse(
            vitalSignsResponses, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }

    private VitalSigns mapToDomain(VitalSignsEntity entity) {
        return VitalSigns.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .surgeryId(entity.getSurgeryId())
                .systolicBloodPressure(entity.getSystolicBloodPressure())
                .diastolicBloodPressure(entity.getDiastolicBloodPressure())
                .heartRate(entity.getHeartRate())
                .respiratoryRate(entity.getRespiratoryRate())
                .temperature(entity.getTemperature())
                .oxygenSaturation(entity.getOxygenSaturation())
                .weight(entity.getWeight())
                .height(entity.getHeight())
                .bmi(entity.getBmi())
                .bmiClassification(entity.getBmiClassification())
                .capillaryGlucose(entity.getCapillaryGlucose())
                .glasgowScoreMotor(entity.getGlasgowScoreMotor())
                .glasgowScoreVerbal(entity.getGlasgowScoreVerbal())
                .glasgowScoreOcular(entity.getGlasgowScoreOcular())
                .observations(entity.getObservations())
                .recordedAt(entity.getRecordedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .process(entity.getProcess())
                .process(entity.getProcess())
                .build();
    }

    private VitalSignsEntity mapToEntity(VitalSigns vitalSigns) {
        return VitalSignsEntity.builder()
                .id(vitalSigns.getId())
                .patientId(vitalSigns.getPatientId())
                .surgeryId(vitalSigns.getSurgeryId())
                .systolicBloodPressure(vitalSigns.getSystolicBloodPressure())
                .diastolicBloodPressure(vitalSigns.getDiastolicBloodPressure())
                .heartRate(vitalSigns.getHeartRate())
                .respiratoryRate(vitalSigns.getRespiratoryRate())
                .temperature(vitalSigns.getTemperature())
                .oxygenSaturation(vitalSigns.getOxygenSaturation())
                .weight(vitalSigns.getWeight())
                .height(vitalSigns.getHeight())
                .bmi(vitalSigns.getBmi())
                .bmiClassification(vitalSigns.getBmiClassification())
                .capillaryGlucose(vitalSigns.getCapillaryGlucose())
                .glasgowScoreMotor(vitalSigns.getGlasgowScoreMotor())
                .glasgowScoreVerbal(vitalSigns.getGlasgowScoreVerbal())
                .glasgowScoreOcular(vitalSigns.getGlasgowScoreOcular())
                .observations(vitalSigns.getObservations())
                .recordedAt(vitalSigns.getRecordedAt())
                .process(vitalSigns.getProcess())
                .createdAt(vitalSigns.getCreatedAt())
                .updatedAt(vitalSigns.getUpdatedAt())
                .createdBy(vitalSigns.getCreatedBy())
                .updatedBy(vitalSigns.getUpdatedBy())
                .build();
    }
    
    /**
     * Calcula el IMC y establece tanto el IMC como su clasificación en el objeto VitalSigns
     */
    private void calculateAndSetBMI(VitalSigns vitalSigns) {
        if (vitalSigns.getWeight() != null && vitalSigns.getHeight() != null && vitalSigns.getHeight() > 0) {
            // La altura debe estar en metros, es posible que necesite convertir si viene en cm
            double heightInMeters = vitalSigns.getHeight();
            if (heightInMeters > 3) { // Asumimos que si es mayor a 3, está en cm
                heightInMeters = heightInMeters / 100;
            }
            
            Double bmi = BMIClassification.calculateBMI(vitalSigns.getWeight(), heightInMeters);
            vitalSigns.setBmi(bmi);
            
            BMIClassification.Classification classification = BMIClassification.getClassification(bmi);
            if (classification != null) {
                vitalSigns.setBmiClassification(classification.getDescription());
            }
        }
    }
}