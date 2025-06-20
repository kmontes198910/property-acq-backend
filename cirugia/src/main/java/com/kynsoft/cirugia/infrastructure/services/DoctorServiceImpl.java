package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.application.response.DoctorResponse;
import com.kynsoft.cirugia.domain.dto.DoctorDto;
import com.kynsoft.cirugia.domain.service.IDoctorService;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.entities.Doctor;
import com.kynsoft.cirugia.infrastructure.repository.command.DoctorWriteDataJPARepository;
import com.kynsoft.cirugia.infrastructure.repository.query.DoctorReadDataJPARepository;
import com.kynsoft.cirugia.infrastructure.services.http.DoctorHttpUUIDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorWriteDataJPARepository repositoryCommand;
    private final DoctorReadDataJPARepository repositoryQuery;
    private final DoctorHttpUUIDService doctorHttpUUIDService;

    public DoctorServiceImpl(DoctorWriteDataJPARepository repositoryCommand, 
                             DoctorReadDataJPARepository repositoryQuery,
                             DoctorHttpUUIDService doctorHttpUUIDService) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.doctorHttpUUIDService = doctorHttpUUIDService;
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.TEAM_MEDICAL_CACHE, allEntries = true)
    public void create(DoctorDto object) {
        this.repositoryCommand.save(new Doctor(object));
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.TEAM_MEDICAL_CACHE, allEntries = true)
    public void update(DoctorDto objectDto) {
        Doctor update = new Doctor(objectDto);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.TEAM_MEDICAL_CACHE, allEntries = true)
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
//    @Cacheable(value = SurgeryCacheConfig.TEAM_MEDICAL_CACHE, key = "#id")
    public DoctorDto findById(UUID id) {
        log.error("Searching for doctor with ID: {}", id);
        Optional<Doctor> patient = this.repositoryQuery.findById(id);
        if (patient.isPresent()) {
            log.error("Doctor found in local repository: {}", patient.get().getId());
            return patient.get().toAggregate();
        } else {
            log.error("Doctor not found in local repository, fetching from external service: {}", id);
            DoctorHttp doctorHttp = this.doctorHttpUUIDService.sendGetHttpRequest(id);
            log.error("Doctor fetched from external service: {}", doctorHttp.getId());
            DoctorDto doctorDto = new DoctorDto(
                    doctorHttp.getId(),
                    doctorHttp.getName(),
                    doctorHttp.getLastName(),
                    doctorHttp.getIdentification(),
                    doctorHttp.getRegisterNumber());
            this.repositoryCommand.save(new Doctor(doctorDto));
            log.error("Doctor saved to local repository: {}", doctorDto.getId());
            return doctorDto;
        }
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.TEAM_MEDICAL_CACHE, 
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
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
