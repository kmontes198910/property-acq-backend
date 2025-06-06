package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.ExpensesResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ExpensesDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.ExpensesNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.ExpresesForPropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IExpensesService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Expenses;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ExpensesWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ExpensesReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class ExpensesServiceImpl implements IExpensesService {

    private final ExpensesReadDataJPARepository repositoryQuery;
    private final ExpensesWriteDataJPARepository repositoryCommand;

    public ExpensesServiceImpl(ExpensesReadDataJPARepository repositoryQuery,
            ExpensesWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(ExpensesDto object) {
        return repositoryCommand.save(new Expenses(object)).getId();
    }

    @Override
    @Transactional
    public void update(ExpensesDto object) {
        Expenses update = this.findByIdSimple(object.getId());

        update.setTotalAmountExpenses(object.getTotalAmountExpenses());
        update.setIncreaseRate(object.getIncreaseRate());
        update.setAccounting(object.getAccounting());
        update.setElectricity(object.getElectricity());
        update.setGas(object.getGas());
        update.setMortgageInsurance(object.getMortgageInsurance());
        update.setPoolSpaService(object.getPoolSpaService());
        update.setSewerWater(object.getSewerWater());
        update.setTrash(object.getTrash());
        update.setAdvertising(object.getAdvertising());
        update.setFireInsurance(object.getFireInsurance());
        update.setJanitorialService(object.getJanitorialService());
        update.setLiabilityInsurance(object.getLiabilityInsurance());
        update.setOtherUtilities(object.getOtherUtilities());
        update.setPropertyTaxes(object.getPropertyTaxes());
        update.setSupplies(object.getSupplies());
        update.setWorkmans(object.getWorkmans());
        update.setAssociationFees(object.getAssociationFees());
        update.setFloodInsurance(object.getFloodInsurance());
        update.setLandscaping(object.getLandscaping());
        update.setLicenses(object.getLicenses());
        update.setPayroll(object.getPayroll());
        update.setRepairMaintenance(object.getRepairMaintenance());
        update.setTelephone(object.getTelephone());
        update.setMiscellaneous(object.getMiscellaneous());
        update.setLegal(object.getLegal());
        update.setIncreaseType(object.getIncreaseType());

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public ExpensesDto findById(UUID id) {
        Optional<Expenses> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new ExpensesNotFoundException(id);
    }

    private Expenses findByIdSimple(UUID id) {
        Optional<Expenses> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new ExpensesNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Expenses> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Expenses> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Expenses> data) {
        List<ExpensesResponse> objects = new ArrayList<>();
        for (Expenses p : data.getContent()) {
            objects.add(new ExpensesResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public ExpensesDto findByPropertyId(String propertyId) {
        Optional<Expenses> entity = repositoryQuery.findByPropertyId(propertyId);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new ExpresesForPropertyNotFoundException();
    }
}
