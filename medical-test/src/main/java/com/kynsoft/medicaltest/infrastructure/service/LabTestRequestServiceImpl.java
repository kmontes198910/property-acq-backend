package com.kynsoft.medicaltest.infrastructure.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.medicaltest.application.query.labTestRequest.getbyid.LabTestRequestResponse;
import com.kynsoft.medicaltest.application.query.labTestRequest.search.LabTestRequestSearchResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestRequestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.Patient;
import com.kynsoft.medicaltest.infrastructure.repository.command.LabTestRequestWriteRepository;
import com.kynsoft.medicaltest.infrastructure.repository.query.LabTestRequestReadRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class LabTestRequestServiceImpl implements ILabTestRequestService {

    private final LabTestRequestReadRepository readRepository;
    private final LabTestRequestWriteRepository writeRepository;

    @Override
    @Transactional(readOnly = true)
    public LabTestRequestDto findById(UUID id) {
        LabTestRequestEntity entity = readRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Parámetro de examen no encontrado con ID: " + id));
        return entity.toAggregate();
    }

    @Override
    @Transactional
    public void create(LabTestRequestDto labTest) {
        LabTestRequestEntity labTestRequestEntity =  new LabTestRequestEntity(labTest);
        writeRepository.save(labTestRequestEntity);
    }

    @Override
    @Transactional
    public void update(LabTestRequestDto dto) {
        LabTestRequestEntity entity = readRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Parámetro de examen no encontrado con ID: " + dto.getId()));

        // Actualizar solo los campos permitidos
        entity.setStatus(dto.getStatus());
        entity.setObservations(dto.getObservations());
        entity.setUpdatedBy(dto.getUpdatedBy());
        writeRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!readRepository.existsById(id)) {
            throw new NoSuchElementException("Parámetro de examen no encontrado con ID: " + id);
        }
        writeRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<LabTestRequestEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<LabTestRequestEntity> data = this.readRepository.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<LabTestRequestEntity> data) {
        List<LabTestRequestSearchResponse> objects = new ArrayList<>();
        for (LabTestRequestEntity p : data.getContent()) {
            objects.add(new LabTestRequestSearchResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    /**
     * Mapea una entidad LabTestParameterEntity a un DTO LabTestParameterDto
     *
     * @param entity La entidad a mapear
     * @return El DTO mapeado
     */
    private LabTestRequestDto mapToDomain(LabTestRequestEntity entity) {
        return LabTestRequestDto.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .doctorId(entity.getDoctorId())
                .creationDate(entity.getCreationDate())
                .status(entity.getStatus())
                .observations(entity.getObservations())
                .businessId(entity.getBusinessId())
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .patient(entity.getPatient().toAggregate())
                .build();
    }
}
