package com.kynsof.treatments.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.medicine.getbyid.MedicinesResponse;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.service.IMedicinesService;
import com.kynsof.treatments.infrastructure.entity.Medicines;
import com.kynsof.treatments.infrastructure.entity.Procedure;
import com.kynsof.treatments.infrastructure.repositories.command.MedicinesWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.MedicinesReadDataJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicinesServiceImpl implements IMedicinesService {

    private final MedicinesReadDataJPARepository repositoryQuery;

    private final MedicinesWriteDataJPARepository repositoryCommand;

    public MedicinesServiceImpl(MedicinesReadDataJPARepository repositoryQuery, MedicinesWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public void create(MedicinesDto medicines) {
        this.repositoryCommand.save(new Medicines(medicines));
    }

    @Override
    public void update(MedicinesDto medicines) {
        Medicines updte = new Medicines(medicines);
        updte.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(updte);
    }

    @Override
    public void delete(MedicinesDto object) {
        try {
            this.repositoryCommand.deleteById(object.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public Long countByNameAndNotId(String name, UUID id) {
        return this.repositoryQuery.countByNameAndNotId(name, id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Procedure> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Medicines> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Medicines> data) {
        List<MedicinesResponse> patients = new ArrayList<>();
        for (Medicines o : data.getContent()) {
            patients.add(new MedicinesResponse(o.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public MedicinesDto findById(UUID id) {
        Optional<Medicines> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.MEDICINES_NOT_FOUND, new ErrorField("id", "Medicione not found.")));
    }
}
