package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.TeamAssignmentResponse;
import com.kynsoft.propertyacqcenter.domain.dto.TeamAssignmentDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.TeamAssignmentNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyContact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.entity.TeamAssignment;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.TeamAssignmentWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.TeamAssignmentReadDataJPARepository;
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
public class TeamAssignmentServiceImpl implements ITeamAssignmentService {

    private final TeamAssignmentReadDataJPARepository repositoryQuery;
    private final TeamAssignmentWriteDataJPARepository repositoryCommand;

    public TeamAssignmentServiceImpl(TeamAssignmentReadDataJPARepository repositoryQuery,
                                     TeamAssignmentWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(TeamAssignmentDto object) {
        return repositoryCommand.save(new TeamAssignment(object)).getId();
    }

    @Override
    @Transactional
    public void update(TeamAssignmentDto object) {
        TeamAssignment update = new TeamAssignment(this.findById(object.getId()));
        update.setBuyerContactRep(object.getBuyerContactRep() != null ? new CompanyContact(object.getBuyerContactRep()) : null);
        update.setBuyerEntityName(object.getBuyerEntityName() != null ? new CompanyContact(object.getBuyerEntityName()) : null);
        update.setLegalContact(object.getLegalContact() != null ? new CompanyContact(object.getLegalContact()) : null);
        update.setLenderCompany(object.getLenderCompany() != null ? new CompanyContact(object.getLenderCompany()) : null);
        update.setProjectManager(object.getProjectManager() != null ? new CompanyContact(object.getProjectManager()) : null);
        update.setProperty(new Property(object.getProperty()));
        update.setTitleEscrowCompany(object.getTitleEscrowCompany() != null ? new CompanyContact(object.getTitleEscrowCompany()) : null);
        update.setSeller(object.getSeller() != null ? new CompanyContact(object.getSeller()) : null);
        update.setHoa(object.getHoa() != null ? new CompanyContact(object.getHoa()) : null);

        update.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public TeamAssignmentDto findById(UUID id) {
        Optional<TeamAssignment> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new TeamAssignmentNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<TeamAssignment> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<TeamAssignment> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<TeamAssignment> data) {
        List<TeamAssignmentResponse> objects = new ArrayList<>();
        for (TeamAssignment p : data.getContent()) {
            objects.add(new TeamAssignmentResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
