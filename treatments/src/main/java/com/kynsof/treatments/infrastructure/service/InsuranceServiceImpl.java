package com.kynsof.treatments.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.domain.dto.InsuranceDto;
import com.kynsof.treatments.domain.service.IInsuranceService;
import com.kynsof.treatments.infrastructure.entity.Insurance;
import com.kynsof.treatments.infrastructure.repositories.command.InsuranceWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.InsuranceReadDataJPARepository;
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
public class InsuranceServiceImpl implements IInsuranceService {

    @Autowired
    private InsuranceReadDataJPARepository repositoryQuery;

    @Autowired
    private InsuranceWriteDataJPARepository repositoryCommand;

    @Override
    public void create(InsuranceDto insurance) {
        this.repositoryCommand.save(new Insurance(insurance));
    }

    @Override
    public void update(InsuranceDto insurance) {
        Insurance update = new Insurance(insurance);
        this.repositoryCommand.save(update);
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public InsuranceDto findById(UUID id) {
        Optional<Insurance> insurance = this.repositoryQuery.findById(id);
        if (insurance.isPresent()) {
            return insurance.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.INSURANCE_NOT_FOUND, new ErrorField("id", "Insurance not found.")));
    }

//    @Override
//    public PaginatedResponse findAll(Pageable pageable) {
//
//        Page<Insurance> data = this.repositoryQuery.findAll(pageable);
//
//        return getPaginatedResponse(data);
//    }

//    @Override
//    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
//        GenericSpecificationsBuilder<Insurance> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
//        Page<Insurance> data = this.repositoryQuery.findAll(specifications, pageable);
//
//        return getPaginatedResponse(data);
//    }
//
//    private PaginatedResponse getPaginatedResponse(Page<Insurance> data) {
//        List<InsuranceResponse> insuranceResponses = new ArrayList<>();
//        for (Insurance p : data.getContent()) {
//            insuranceResponses.add(new InsuranceResponse(p.toAggregate()));
//        }
//        return new PaginatedResponse(insuranceResponses, data.getTotalPages(), data.getNumberOfElements(),
//                data.getTotalElements(), data.getSize(), data.getNumber());
//    }
}
