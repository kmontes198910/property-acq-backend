package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.ClosingCostsResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ClosingCostsDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PurchaseForPropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PurchaseNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IClosingCostsService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.ClosingCosts;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ClosingCostsWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ClosingCostsReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class ClosingCostsServiceImpl implements IClosingCostsService {

    private final ClosingCostsReadDataJPARepository repositoryQuery;
    private final ClosingCostsWriteDataJPARepository repositoryCommand;

    public ClosingCostsServiceImpl(ClosingCostsReadDataJPARepository repositoryQuery,
                               ClosingCostsWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(ClosingCostsDto object) {
        return repositoryCommand.save(new ClosingCosts(object)).getId();
    }

    @Override
    @Transactional
    public void update(ClosingCostsDto object) {
        ClosingCosts update = this.findByIdSimple(object.getId());

        update.setProperty(new Property(object.getProperty()));
        update.setAdministrationFee(object.getAdministrationFee());
        update.setAttorneyFee(object.getAttorneyFee());
        update.setDocumentPreparation(object.getDocumentPreparation());
        update.setMortgageBrokerFee(object.getMortgageBrokerFee());
        update.setProcessing(object.getProcessing());
        update.setTaxService(object.getTaxService());
        update.setTitleSearch(object.getTitleSearch());
        update.setApplicationFee(object.getApplicationFee());
        update.setCommitmentFee(object.getCommitmentFee());
        update.setFloodCertification(object.getFloodCertification());
        update.setPestInspection(object.getPestInspection());
        update.setRecordingFee(object.getRecordingFee());
        update.setTaxes(object.getTaxes());
        update.setUnderwriting(object.getUnderwriting());
        update.setAppraisal(object.getAppraisal());
        update.setCreditReport(object.getCreditReport());
        update.setFundingFee(object.getFundingFee());
        update.setPoints(object.getPoints());
        update.setSurvey(object.getSurvey());
        update.setTitleInsurance(object.getTitleInsurance());
        update.setOtherFees(object.getOtherFees());

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
    public ClosingCostsDto findById(UUID id) {
        Optional<ClosingCosts> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PurchaseNotFoundException(id);
    }

    private ClosingCosts findByIdSimple(UUID id) {
        Optional<ClosingCosts> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new PurchaseNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<ClosingCosts> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<ClosingCosts> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<ClosingCosts> data) {
        List<ClosingCostsResponse> objects = new ArrayList<>();
        for (ClosingCosts p : data.getContent()) {
            objects.add(new ClosingCostsResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public ClosingCostsDto findByPropertyId(String propertyId) {
        Optional<ClosingCosts> entity = repositoryQuery.findByPropertyId(propertyId);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PurchaseForPropertyNotFoundException();
    }
}
