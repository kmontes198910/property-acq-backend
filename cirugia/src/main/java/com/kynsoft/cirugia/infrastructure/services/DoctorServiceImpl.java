package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.application.response.DoctorResponse;
import com.kynsoft.cirugia.domain.dto.DoctorDto;
import com.kynsoft.cirugia.domain.dto.exception.DoctorNotFoundException;
import com.kynsoft.cirugia.domain.service.IDoctorService;
import com.kynsoft.cirugia.infrastructure.entities.Doctor;
import com.kynsoft.cirugia.infrastructure.repository.command.DoctorWriteDataJPARepository;
import com.kynsoft.cirugia.infrastructure.repository.query.DoctorReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorWriteDataJPARepository repositoryCommand;
    private final DoctorReadDataJPARepository repositoryQuery;

    public DoctorServiceImpl(DoctorWriteDataJPARepository repositoryCommand, DoctorReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public void create(DoctorDto object) {
        this.repositoryCommand.save(new Doctor(object));
    }

    @Override
    public void update(DoctorDto objectDto) {
        Doctor update = new Doctor(objectDto);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public DoctorDto findById(UUID id) {
        Optional<Doctor> patient = this.repositoryQuery.findById(id);
        if (patient.isPresent()) {
            return patient.get().toAggregate();
        }
        throw new DoctorNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Doctor> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Doctor> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Doctor> data) {
        List<DoctorResponse> patients = new ArrayList<>();
        for (Doctor s : data.getContent()) {
            patients.add(new DoctorResponse(s.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
