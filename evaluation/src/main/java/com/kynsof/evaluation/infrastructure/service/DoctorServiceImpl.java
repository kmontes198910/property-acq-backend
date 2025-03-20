package com.kynsof.evaluation.infrastructure.service;

import com.kynsof.evaluation.domain.dto.DoctorDto;
import com.kynsof.evaluation.domain.dto.enumDto.Status;
import com.kynsof.evaluation.domain.service.IDoctorService;
import com.kynsof.evaluation.infrastructure.entity.Doctor;
import com.kynsof.evaluation.infrastructure.repositories.command.DoctorWriteDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.query.DoctorReadDataJPARepository;
import com.kynsof.evaluation.infrastructure.service.http.DoctorHttpUUIDService;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import com.kynsof.share.core.domain.response.ErrorField;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorWriteDataJPARepository repositoryCommand;
    private final DoctorReadDataJPARepository repositoryQuery;
    private final DoctorHttpUUIDService doctorHttpUUIDService;

    public DoctorServiceImpl(DoctorHttpUUIDService doctorHttpUUIDService, DoctorReadDataJPARepository repositoryQuery,
                             DoctorWriteDataJPARepository repositoryCommand) {
        this.doctorHttpUUIDService = doctorHttpUUIDService;
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public UUID create(DoctorDto doctorDto) {
        Doctor entity = this.repositoryCommand.save(new Doctor(doctorDto));
        return entity.getId();
    }

    @Override
    public UUID update(DoctorDto doctorDto) {
        if (doctorDto == null || doctorDto.getId() == null) {
            throw new IllegalArgumentException("Doctor DTO or ID cannot be null");
        }

        this.repositoryQuery.findById(doctorDto.getId())
                .map(patient -> {
                    if (doctorDto.getName() != null) {
                        patient.setName(doctorDto.getName());
                    }
                    if (doctorDto.getStatus() != null) {
                        patient.setStatus(doctorDto.getStatus());
                    }
                    if (doctorDto.getImage() != null) {
                        patient.setImage(doctorDto.getImage());
                    }

                    return this.repositoryCommand.save(patient);
                })
                .orElseThrow(() -> new EntityNotFoundException("Doctor with ID " + doctorDto.getId() + " not found"));

        return doctorDto.getId();
    }

    @Override
    public DoctorDto findById(UUID id) {
        Optional<Doctor> patient = this.repositoryQuery.findById(id);
        if (patient.isPresent()) {
            return patient.get().toAggregate();
        }
        {
            DoctorHttp doctorHttp = this.doctorHttpUUIDService.sendGetHttpRequest(id);
            DoctorDto doctorDto = new DoctorDto(
                    doctorHttp.getId(),
                    doctorHttp.getIdentification(),
                    doctorHttp.getName(),
                    doctorHttp.getLastName(),
                    doctorHttp.getRegisterNumber(),
                    doctorHttp.getImage(),
                    Status.valueOf(doctorHttp.getStatus())
            );
            this.repositoryCommand.save(new Doctor(doctorDto));
            return doctorDto;
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

}
