package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.procedure.getAll.ProcedureResponse;
import com.kynsof.treatments.domain.dto.ProcedureDto;
import com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory;
import com.kynsof.treatments.domain.service.IProcedureService;
import com.kynsof.treatments.infrastructure.entity.Procedure;
import com.kynsof.treatments.infrastructure.repositories.command.ProcedureWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.ProcedureReadDataJPARepository;
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
public class ProcedureServiceImpl implements IProcedureService {

    @Autowired
    private ProcedureReadDataJPARepository repositoryQuery;

    @Autowired
    private ProcedureWriteDataJPARepository repositoryCommand;

    @Override
    public void create(ProcedureDto patients) {
        this.repositoryCommand.save(new Procedure(patients));
    }

    @Override
    public void update(ProcedureDto patients) {
        Procedure update = new Procedure(patients);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
    public void delete(ProcedureDto object) {
        try {
            this.repositoryCommand.deleteById(object.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        filterCreteria(filterCriteria);
        GenericSpecificationsBuilder<Procedure> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Procedure> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private void filterCreteria(List<FilterCriteria> filterCriteria) {
        for (FilterCriteria filter : filterCriteria) {
            if ("type".equals(filter.getKey()) && filter.getValue() instanceof String) {
                try {
                    MedicalExamCategory enumValue = MedicalExamCategory.valueOf((String) filter.getValue());
                    filter.setValue(enumValue);
                } catch (IllegalArgumentException e) {
                    System.err.println("Valor inválido para el tipo Enum MedicalExamCategory: " + filter.getValue());
                }
            }
        }
    }

    private PaginatedResponse getPaginatedResponse(Page<Procedure> data) {
        List<ProcedureResponse> patients = new ArrayList<>();
        for (Procedure o : data.getContent()) {
            patients.add(new ProcedureResponse(o.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public ProcedureDto findByCode(String code) {
        Optional<Procedure> procedureByCode = Optional.ofNullable(this.repositoryQuery.findProcedureByCode(code));
        if (procedureByCode.isPresent()) {
            return procedureByCode.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PROCEDURE_NOT_FOUND, new ErrorField("id", "Procedure not found.")));
    }

    @Override
    public ProcedureDto findById(UUID id) {
        Optional<Procedure> procedure = this.repositoryQuery.findById(id);
        if (procedure.isPresent()) {
            return procedure.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PROCEDURE_NOT_FOUND, new ErrorField("id", "Procedure not found.")));
    }

//    @Override
//    public PaginatedResponse findAll(Pageable pageable, String name, String code, String type) {
//        ProcedureSpecifications specifications = new ProcedureSpecifications(code, name, type);
//        Page<Procedure> data = this.repositoryQuery.findAll(specifications, pageable);
//
//        List<ProcedureResponse> procedureResponses = new ArrayList<>();
//        for (Procedure p : data.getContent()) {
//            procedureResponses.add(new ProcedureResponse(p.toAggregate()));
//        }
//        return new PaginatedResponse(procedureResponses, data.getTotalPages(), data.getNumberOfElements(),
//                data.getTotalElements(), data.getSize(), data.getNumber());
//    }

    @Override
    public Long countByCodeAndNotId(String code, UUID id) {
        return this.repositoryQuery.countByCodeAndNotId(code, id);
    }

    @Override
    public Long countByNameAndNotId(String name, UUID id) {
        return this.repositoryQuery.countByNameAndNotId(name, id);
    }
}
