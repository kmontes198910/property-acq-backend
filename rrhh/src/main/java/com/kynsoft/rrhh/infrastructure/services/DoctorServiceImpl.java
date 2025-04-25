package com.kynsoft.rrhh.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.rrhh.application.query.doctor.getbyid.DoctorResponse;
import com.kynsoft.rrhh.domain.dto.DoctorDto;
import com.kynsoft.rrhh.domain.interfaces.services.IDoctorService;
import com.kynsoft.rrhh.infrastructure.config.RRHHCacheConfig;
import com.kynsoft.rrhh.infrastructure.identity.Doctor;
import com.kynsoft.rrhh.infrastructure.repository.command.DoctorWriteDataJPARepository;
import com.kynsoft.rrhh.infrastructure.repository.query.DoctorReadDataJPARepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorWriteDataJPARepository repositoryCommand;

    private final DoctorReadDataJPARepository repositoryQuery;

    public DoctorServiceImpl(DoctorWriteDataJPARepository repositoryCommand, DoctorReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.DOCTORS_CACHE, RRHHCacheConfig.DOCTORS_BY_ID_CACHE}, allEntries = true)
    public void create(DoctorDto object) {
        this.repositoryCommand.save(new Doctor(object));
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.DOCTORS_CACHE, RRHHCacheConfig.DOCTORS_BY_ID_CACHE}, allEntries = true)
    public void update(DoctorDto object) {
        Doctor update = new Doctor(object);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.DOCTORS_CACHE, RRHHCacheConfig.DOCTORS_BY_ID_CACHE}, allEntries = true)
    public void delete(DoctorDto object) {
        this.repositoryCommand.save(new Doctor(object));
    }

    @Override
    @Cacheable(value = RRHHCacheConfig.DOCTORS_BY_ID_CACHE, key = "#id")
    public DoctorDto findById(UUID id) {

        Optional<Doctor> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.DEVICE_NOT_FOUND, new ErrorField("Doctor.id", "Doctor not found.")));

    }

    @Override
    @Cacheable(value = RRHHCacheConfig.DOCTOR_SEARCH_CACHE,
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Doctor> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Doctor> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Doctor> data) {
        List<DoctorResponse> patients = new ArrayList<>();
        for (Doctor o : data.getContent()) {
            patients.add(new DoctorResponse(o.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public Long countByIdentification(String identification) {
        return this.repositoryQuery.countByIdentification(identification);
    }

    @Override
    public Long countByEmail(String email) {
        return this.repositoryQuery.countByEmail(email);
    }

    @Override
    public Long countByIdentificationAndNotId(String identification, UUID id) {
        return this.repositoryQuery.countByIdentificationAndNotId(identification, id);
    }

    @Override
    public Long countByEmailAndNotId(String email, UUID id) {
        return this.repositoryQuery.countByEmailAndNotId(email, id);
    }

    @Override
    public Long countByCodeAndNotId(String code, UUID id) {
        return this.repositoryQuery.countByCodeAndNotId(code, id);
    }

    @Override
    @Cacheable(value = RRHHCacheConfig.DOCTORS_CACHE)
    public List<DoctorDto> findAllToReplicate() {
        List<Doctor> objects = this.repositoryQuery.findAll();
        List<DoctorDto> objectDtos = new ArrayList<>();

        for (Doctor object : objects) {
            objectDtos.add(object.toAggregate());
        }

        return objectDtos;
    }

}
