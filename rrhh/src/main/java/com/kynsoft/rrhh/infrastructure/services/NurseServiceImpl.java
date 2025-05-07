package com.kynsoft.rrhh.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.rrhh.application.query.nurse.getbyid.NurseResponse;
import com.kynsoft.rrhh.domain.dto.NurseDto;
import com.kynsoft.rrhh.domain.interfaces.services.INurseService;
import com.kynsoft.rrhh.infrastructure.identity.Nurse;
import com.kynsoft.rrhh.infrastructure.repository.command.NurseWriteDataJPARepository;
import com.kynsoft.rrhh.infrastructure.repository.query.NurseReadDataJPARepository;
import com.kynsoft.rrhh.infrastructure.util.RRHHCacheConfig;
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
public class NurseServiceImpl implements INurseService {

    private final NurseWriteDataJPARepository repositoryCommand;
    private final NurseReadDataJPARepository repositoryQuery;

    public NurseServiceImpl(NurseWriteDataJPARepository repositoryCommand, NurseReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.NURSES_CACHE, RRHHCacheConfig.NURSES_BY_ID_CACHE}, allEntries = true)
    public void create(NurseDto object) {
        this.repositoryCommand.save(new Nurse(object));
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.NURSES_CACHE, RRHHCacheConfig.NURSES_BY_ID_CACHE}, allEntries = true)
    public void update(NurseDto object) {
        Nurse update = new Nurse(object);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.NURSES_CACHE, RRHHCacheConfig.NURSES_BY_ID_CACHE}, allEntries = true)
    public void delete(NurseDto object) {
        this.repositoryCommand.save(new Nurse(object));
    }

    @Override
    @Cacheable(value = RRHHCacheConfig.NURSES_BY_ID_CACHE, key = "#id")
    public NurseDto findById(UUID id) {
        Optional<Nurse> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.DEVICE_NOT_FOUND, new ErrorField("Nurse.id", "Nurse not found.")));
    }

    @Override
    public NurseDto findByIdentification(String identification) {
        Optional<Nurse> object = this.repositoryQuery.findByIdentification(identification);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.DEVICE_NOT_FOUND, new ErrorField("Nurse.identification", "Nurse not found.")));
    }

    @Override
    @Cacheable(value = RRHHCacheConfig.NURSE_SEARCH_CACHE,
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Nurse> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Nurse> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Nurse> data) {
        List<NurseResponse> nurses = new ArrayList<>();
        for (Nurse o : data.getContent()) {
            nurses.add(new NurseResponse(o.toAggregate()));
        }
        return new PaginatedResponse(nurses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}