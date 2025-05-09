package com.kynsoft.cirugia.infrastructure.services;

import com.kynsoft.cirugia.domain.dto.Anesthesia;
import com.kynsoft.cirugia.domain.service.IAnesthesiaService;
import com.kynsoft.cirugia.infrastructure.entities.AnesthesiaEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.AnesthesiaWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.AnesthesiaReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AnesthesiaRepositoryImpl implements IAnesthesiaService {

    private final AnesthesiaReadRepository anesthesiaReadRepository;
    private final AnesthesiaWriteRepository anesthesiaWriteRepository;

    @Autowired
    public AnesthesiaRepositoryImpl(AnesthesiaReadRepository anesthesiaReadRepository,
                                 AnesthesiaWriteRepository anesthesiaWriteRepository) {
        this.anesthesiaReadRepository = anesthesiaReadRepository;
        this.anesthesiaWriteRepository = anesthesiaWriteRepository;
    }


    private Anesthesia mapToDomain(AnesthesiaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        try {
            return Anesthesia.builder()
                    .id(entity.getId())
                    .surgeryId(entity.getSurgeryId())
                    .anesthesiaType(entity.getAnesthesiaType())
                    .mouthOpening(entity.getMouthOpening())
                    .thyroMentalDistance(entity.getThyroMentalDistance())
                    .neckCircumference(entity.getNeckCircumference())
                    .mallampati(entity.getMallampati())
                    .cervicalMobility(entity.getCervicalMobility())
                    .mandibularProtrusion(entity.getMandibularProtrusion())
                    .difficultIntubationHistory(entity.getDifficultIntubationHistory())
                    .intubationDifficulties(entity.getIntubationDifficulties())
                    .thoraxDescription(entity.getThoraxDescription())
                    .heartDescription(entity.getHeartDescription())
                    .lungsDescription(entity.getLungsDescription())
                    .abdomenDescription(entity.getAbdomenDescription())
                    .extremitiesDescription(entity.getExtremitiesDescription())
                    .centralNervousSystem(entity.getCentralNervousSystem())
                    .asaPhysicalStatus(entity.getAsaPhysicalStatus())
                    .operationRisks(entity.getOperationRisks())
                    .metabolicEquivalent(entity.getMetabolicEquivalent())
                    .lastIntoxicationHours(entity.getLastIntoxicationHours())
                    .anesthetics(entity.getAnesthetics())
                    .surgicalDrugs(entity.getSurgicalDrugs())
                    .allergies(entity.getAllergies())
                    .transfusions(entity.getTransfusions())
                    .habits(entity.getHabits())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .createdBy(entity.getCreatedBy())
                    .updatedBy(entity.getUpdatedBy())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    private AnesthesiaEntity mapToEntity(Anesthesia domain) {
        if (domain == null) {
            return null;
        }
        
        try {
            return AnesthesiaEntity.builder()
                    .id(domain.getId())
                    .surgeryId(domain.getSurgeryId())
                    .anesthesiaType(domain.getAnesthesiaType())
                    .mouthOpening(domain.getMouthOpening())
                    .thyroMentalDistance(domain.getThyroMentalDistance())
                    .neckCircumference(domain.getNeckCircumference())
                    .mallampati(domain.getMallampati())
                    .cervicalMobility(domain.getCervicalMobility())
                    .mandibularProtrusion(domain.getMandibularProtrusion())
                    .difficultIntubationHistory(domain.getDifficultIntubationHistory())
                    .intubationDifficulties(domain.getIntubationDifficulties())
                    .thoraxDescription(domain.getThoraxDescription())
                    .heartDescription(domain.getHeartDescription())
                    .lungsDescription(domain.getLungsDescription())
                    .abdomenDescription(domain.getAbdomenDescription())
                    .extremitiesDescription(domain.getExtremitiesDescription())
                    .centralNervousSystem(domain.getCentralNervousSystem())
                    .asaPhysicalStatus(domain.getAsaPhysicalStatus())
                    .operationRisks(domain.getOperationRisks())
                    .metabolicEquivalent(domain.getMetabolicEquivalent())
                    .lastIntoxicationHours(domain.getLastIntoxicationHours())
                    .anesthetics(domain.getAnesthetics())
                    .surgicalDrugs(domain.getSurgicalDrugs())
                    .allergies(domain.getAllergies())
                    .transfusions(domain.getTransfusions())
                    .habits(domain.getHabits())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .createdBy(domain.getCreatedBy())
                    .updatedBy(domain.getUpdatedBy())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String createAnesthesia(Anesthesia anesthesia) {
        if (anesthesia.getId() == null) {
            anesthesia.setId(UUID.randomUUID());
        }
        
        if (anesthesia.getCreatedAt() == null) {
            anesthesia.setCreatedAt(LocalDateTime.now());
        }
        
        AnesthesiaEntity savedEntity = anesthesiaWriteRepository.save(mapToEntity(anesthesia));
        return savedEntity.getId().toString();
    }

    @Override
    public void updateAnesthesia(Anesthesia anesthesia) {
        if (anesthesia.getId() == null) {
            throw new IllegalArgumentException("No se puede actualizar una anestesia sin ID");
        }
        
        // Verificar que la anestesia existe
        anesthesiaReadRepository.findById(anesthesia.getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe una anestesia con el ID: " + anesthesia.getId()));
        
        // Actualizar la fecha de modificación
        anesthesia.setUpdatedAt(LocalDateTime.now());
        
        // Guardar los cambios
        anesthesiaWriteRepository.save(mapToEntity(anesthesia));
    }

    @Override
    public Optional<Anesthesia> getAnesthesiaById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return anesthesiaReadRepository.findById(uuid)
                    .map(this::mapToDomain);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Anesthesia> getAnesthesiaBySurgeryId(UUID surgeryId) {
        return anesthesiaReadRepository.findBySurgeryId(surgeryId)
                .map(this::mapToDomain);
    }
}