package com.kynsoft.rrhh.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.rrhh.application.query.users.getbyid.UserSystemsByIdResponse;
import com.kynsoft.rrhh.domain.dto.UserSystemDto;
import com.kynsoft.rrhh.domain.interfaces.services.IUserSystemService;
import com.kynsoft.rrhh.infrastructure.config.RRHHCacheConfig;
import com.kynsoft.rrhh.infrastructure.identity.Assistant;
import com.kynsoft.rrhh.infrastructure.identity.Doctor;
import com.kynsoft.rrhh.infrastructure.identity.UserSystem;
import com.kynsoft.rrhh.infrastructure.repository.command.UserSystemsWriteDataJPARepository;
import com.kynsoft.rrhh.infrastructure.repository.query.UserSystemReadDataJPARepository;
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
public class UserSystemServiceImpl implements IUserSystemService {

    private final UserSystemsWriteDataJPARepository repositoryCommand;

    private final UserSystemReadDataJPARepository repositoryQuery;

    public UserSystemServiceImpl(UserSystemsWriteDataJPARepository repositoryCommand, UserSystemReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.USERS_BY_ID_CACHE, RRHHCacheConfig.USERS_BY_IDENTIFICATION_CACHE}, allEntries = true)
    public UUID create(UserSystemDto userSystemDto) {
        UserSystem data = new UserSystem(userSystemDto);
        UserSystem userSystem = this.repositoryCommand.save(data);
        return userSystem.getId();
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.USERS_BY_ID_CACHE, RRHHCacheConfig.USERS_BY_IDENTIFICATION_CACHE}, allEntries = true)
    public void update(UserSystemDto userSystemDto) {
        UserSystem update = new UserSystem(userSystemDto);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
    @CacheEvict(value = {RRHHCacheConfig.USERS_BY_ID_CACHE, RRHHCacheConfig.USERS_BY_IDENTIFICATION_CACHE}, allEntries = true)
    public void delete(UUID id) {
    }

    @Override
    @Cacheable(value = RRHHCacheConfig.USERS_BY_ID_CACHE, key = "#id")
    public UserSystemDto findById(UUID id) {
        Optional<UserSystem> userSystem = this.repositoryQuery.findById(id);
        if (userSystem.isPresent()) {
            return userSystem.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.USER_NOT_FOUND, new ErrorField("User.id", "User not found.")));
    }
    
    @Override
    @Cacheable(value = RRHHCacheConfig.USERS_BY_IDENTIFICATION_CACHE, key = "#identification")
    public UserSystemDto getUserByIdentification(String identification) {
        Optional<UserSystem> userSystemOptional = repositoryQuery.findByIdentification(identification);
        if (userSystemOptional.isPresent()) {
            UserSystem userSystem = userSystemOptional.get();
            if (userSystem instanceof Doctor) {
                return ((Doctor) userSystem).toAggregate();
            } else if (userSystem instanceof Assistant) {
                return ((Assistant) userSystem).toAggregate();
            } else {
                return userSystem.toAggregate();
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<UserSystem> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<UserSystem> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<UserSystem> data) {
        List<UserSystemsByIdResponse> allergyResponses = new ArrayList<>();
        for (UserSystem p : data.getContent()) {
            allergyResponses.add(new UserSystemsByIdResponse(p.toAggregate()));
        }
        return new PaginatedResponse(allergyResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
