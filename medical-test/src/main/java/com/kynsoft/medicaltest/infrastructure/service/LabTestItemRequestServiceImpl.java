package com.kynsoft.medicaltest.infrastructure.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.medicaltest.application.query.labTestItemRequest.getbyid.LabTestItemRequestResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestItemRequestDto;
import com.kynsoft.medicaltest.domain.service.ILabTestItemRequestService;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestItemRequestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestRequestEntity;
import com.kynsoft.medicaltest.infrastructure.repository.command.LabTestItemRequestWriteRepository;
import com.kynsoft.medicaltest.infrastructure.repository.query.LabTestItemRequestRedRepository;
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
public class LabTestItemRequestServiceImpl implements ILabTestItemRequestService {

    private final LabTestItemRequestRedRepository readRepository;
    private final LabTestItemRequestWriteRepository writeRepository;

    @Override
    @Transactional(readOnly = true)
    public LabTestItemRequestDto findById(UUID id) {
        LabTestItemRequestEntity entity = readRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Parámetro de examen no encontrado con ID: " + id));
        return entity.toAggregateSimple();
    }

    @Override
    @Transactional
    public void create(LabTestItemRequestDto labTest) {
        writeRepository.save(new LabTestItemRequestEntity(labTest));
    }

    @Override
    @Transactional
    public void update(LabTestItemRequestDto dto) {
        LabTestItemRequestEntity entity = readRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Parámetro de examen no encontrado con ID: " + dto.getId()));

        entity.setStatus(dto.getStatus());
        entity.setObservations(dto.getObservations());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setCompletionDate(dto.getCompletionDate());
        entity.setExaminationType(dto.getExaminationType());
        entity.setOrder(new LabTestRequestEntity(dto.getOrder()));

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
        GenericSpecificationsBuilder<LabTestItemRequestEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<LabTestItemRequestEntity> data = this.readRepository.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<LabTestItemRequestEntity> data) {
        List<LabTestItemRequestResponse> objects = new ArrayList<>();
        for (LabTestItemRequestEntity p : data.getContent()) {
            objects.add(new LabTestItemRequestResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
