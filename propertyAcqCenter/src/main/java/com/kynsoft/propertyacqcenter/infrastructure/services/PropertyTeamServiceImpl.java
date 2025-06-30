package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.PropertyTeamResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyTeamDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.TeamAssignmentForPropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.TeamAssignmentNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyTeamService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyContact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.entity.PropertyTeam;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.PropertyTeamWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.PropertyTeamReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class PropertyTeamServiceImpl implements IPropertyTeamService {

    private final PropertyTeamReadDataJPARepository repositoryQuery;
    private final PropertyTeamWriteDataJPARepository repositoryCommand;

    public PropertyTeamServiceImpl(PropertyTeamReadDataJPARepository repositoryQuery,
                                   PropertyTeamWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public void create(List<PropertyTeamDto> dtos) {
        List<PropertyTeam> teams = new ArrayList<>();
        for (PropertyTeamDto dto : dtos) {
            teams.add(new PropertyTeam(dto));
        }
        this.repositoryCommand.saveAll(teams);
    }

    @Override
    @Transactional
    public UUID create(PropertyTeamDto object) {
        return repositoryCommand.save(new PropertyTeam(object)).getId();
    }

    @Override
    @Transactional
    public void update(PropertyTeamDto object) {
        PropertyTeam update = new PropertyTeam(this.findById(object.getId()));

        update.setProfile(object.getProfile());
        update.setContact(new CompanyContact(object.getContact()));
        update.setProperty(new Property(object.getProperty()));
        update.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(update);
    }

    @Override
    public void delete(UUID id) {
        try {
            this.findById(id);
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
    }

    @Override
    public PropertyTeamDto findById(UUID id) {
        Optional<PropertyTeam> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new TeamAssignmentNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<PropertyTeam> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PropertyTeam> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PropertyTeam> data) {
        List<PropertyTeamResponse> objects = new ArrayList<>();
        for (PropertyTeam p : data.getContent()) {
            objects.add(new PropertyTeamResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public PropertyTeamDto findByPropertyId(String propertyId) {
        Optional<PropertyTeam> entity = repositoryQuery.findByPropertyId(propertyId);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new TeamAssignmentForPropertyNotFoundException();
    }

}
