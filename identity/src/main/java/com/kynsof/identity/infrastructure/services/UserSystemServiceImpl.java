package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.application.query.users.getSearch.UserSystemsResponse;
import com.kynsof.identity.domain.dto.UserStatus;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.identity.infrastructure.entities.UserSystem;
import com.kynsof.identity.infrastructure.repository.command.UserSystemsWriteDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.UserSystemReadDataJPARepository;
import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserSystemServiceImpl implements IUserSystemService {

    private final UserSystemsWriteDataJPARepository repositoryCommand;
    private final UserSystemReadDataJPARepository repositoryQuery;

    public UserSystemServiceImpl(UserSystemReadDataJPARepository repositoryQuery, UserSystemsWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(UserSystemDto userSystemDto) {
        var data = new UserSystem(userSystemDto);
        var userSystem = repositoryCommand.save(data);
        return userSystem.getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "userExistsCache", key = "#userSystemDto.email")
    public void update(UserSystemDto userSystemDto) {
        var update = new UserSystem(userSystemDto);
        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UserSystemDto userSystemDto) {
        repositoryCommand.deleteById(userSystemDto.getId());
    }

    @Override
    @Transactional
    public void deleteAll(List<UUID> users) {
        var delete = users.stream()
                .map(this::findById)
                .map(this::deactivateUser)
                .toList();
        repositoryCommand.saveAll(delete);
    }

    private UserSystem deactivateUser(UserSystemDto userSystemDto) {
        var user = new UserSystem(userSystemDto);
        user.setStatus(UserStatus.INACTIVE);
        user.setEmail(user.getEmail() + "-" + UUID.randomUUID());
        user.setUserName(user.getUserName() + "-" + UUID.randomUUID());
        return user;
    }

    @Override
    public UserSystemDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(UserSystem::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.USER_NOT_FOUND, new ErrorField("id", "User not found."))));
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        filterCriteria(filterCriteria);
        var specifications = new GenericSpecificationsBuilder<UserSystem>(filterCriteria);
        var data = repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private void filterCriteria(List<FilterCriteria> filterCriteria) {
        filterCriteria.forEach(filter -> {
            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
                filter.setValue(parseEnum(UserStatus.class, (String) filter.getValue(), "UserStatus"));
            } else if ("userType".equals(filter.getKey()) && filter.getValue() instanceof String) {
                filter.setValue(parseEnum(EUserType.class, (String) filter.getValue(), "EUserType"));
            }
        });
    }

    private <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value, String enumName) {
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid value for enum " + enumName + ": " + value);
            return null;
        }
    }

    private PaginatedResponse getPaginatedResponse(Page<UserSystem> data) {
        var userSystemsResponses = data.getContent().stream().distinct()
                .map(UserSystem::toAggregate)
                .map(UserSystemsResponse::new)
                .toList();
        return new PaginatedResponse(userSystemsResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public Long countByUserNameAndNotId(String userName, UUID id) {
        return repositoryQuery.countByUserNameAndNotId(userName, id);
    }

    @Override
    public Long countByEmailAndNotId(String email, UUID id) {
        return repositoryQuery.countByEmailAndNotId(email, id);
    }

    @Override
    public UserSystemDto findByEmail(String email) {
        return repositoryQuery.findByEmail(email)
                .map(UserSystem::toAggregate)
                .orElse(null);
    }

    @Override
    @Cacheable(value = "userExistsCache", key = "#email")
    public boolean existsByEmailAndStatus(String email) {
        return this.repositoryQuery.existsByEmailAndStatus(email, UserStatus.ACTIVE);
    }

    @Override
    public PaginatedResponse getUsersByBusiness(UUID businessId, String email, String name, String lastName, Pageable pageable) {
        // Validación del businessId para evitar consultas innecesarias
        if (businessId == null) {
            throw new IllegalArgumentException("El businessId no puede ser nulo.");
        }

        // Normalización de los filtros para evitar búsquedas incorrectas
        String filteredEmail = (email != null && !email.trim().isEmpty()) ? email.trim() : null;
        String filteredName = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        String filteredLastName = (lastName != null && !lastName.trim().isEmpty()) ? lastName.trim() : null;

        // Ejecutar la consulta con los filtros opcionales
        Page<UserSystem> usersPage = this.repositoryQuery.findUsersByBusinessAndFilters(
                businessId,
                filteredEmail,
                filteredName,
                filteredLastName,
                pageable
        );

        // Convertir a DTOs
        List<UserSystemDto> usersDtoList = usersPage.getContent().stream()
                .map(UserSystem::toAggregate)
                .collect(Collectors.toList());

        // Construir y devolver la respuesta paginada
        return new PaginatedResponse(
                usersDtoList,
                usersPage.getTotalPages(),
                usersPage.getNumberOfElements(),
                usersPage.getTotalElements(),
                usersPage.getSize(),
                usersPage.getNumber()
        );
    }
}