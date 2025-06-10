package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.IncomeResponse;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.IncomeForPropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.IncomeNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Income;
import com.kynsoft.propertyacqcenter.infrastructure.entity.IncomeDetailsBreakdown;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.IncomeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.IncomeReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class IncomeServiceImpl implements IIncomeService {

    private final IncomeReadDataJPARepository repositoryQuery;
    private final IncomeWriteDataJPARepository repositoryCommand;

    public IncomeServiceImpl(IncomeReadDataJPARepository repositoryQuery,
                             IncomeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(IncomeDto object) {
        return repositoryCommand.save(new Income(object)).getId();
    }

    @Override
    @Transactional
    public void update(IncomeDto object) {
        Income update = this.findByIdSimple(object.getId());
        update.setProperty(new Property(object.getProperty()));
        update.setGrossMonthlyIncome(object.getGrossMonthlyIncome());
        update.setIncreaseRate(object.getIncreaseRate());
        update.setTotalNetMonthlyIncome(object.getTotalNetMonthlyIncome());

        update.setUnitType(object.getUnitType());
        update.setQuantity(object.getQuantity());
        update.setRentMo(object.getRentMo());
        update.setSqft(object.getSqft());
        update.setSqftValue(object.getSqftValue());
        update.setOccupancy(object.getOccupancy());
        update.setAnnualIncrease(object.getAnnualIncrease());
        update.setDepositForfeitures(object.getDepositForfeitures());
        update.setIncomefromInterest(object.getIncomefromInterest());
        update.setVendingMachines(object.getVendingMachines());
        update.setLateCharges(object.getLateCharges());
        update.setLaundryRoom(object.getLaundryRoom());
        update.setOther(object.getOther());
        update.setPropertyManagementRate(object.getPropertyManagementRate());
        update.setLeasingCommissionRate(object.getLeasingCommissionRate());
        update.setLeasingCommision(object.getLeasingCommision());
        update.setPorcentageIncreaseType(object.getPorcentageIncreaseType());
        update.setFixedDollarAmount(object.getFixedDollarAmount());
        update.setIncreaseType(object.getIncreaseType());

        Set<IncomeDetailsBreakdown> detailsBreakdown = new HashSet<>();
        if (object.getDetailsBreakdown() != null) {
            object.getDetailsBreakdown().forEach(dbr -> {
                IncomeDetailsBreakdown i = new IncomeDetailsBreakdown(dbr);
                i.setIncome(update);
                detailsBreakdown.add(i);
            });
        }
        update.setDetailsBreakdown(detailsBreakdown);
        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public IncomeDto findById(UUID id) {
        Optional<Income> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new IncomeNotFoundException(id);
    }

    private Income findByIdSimple(UUID id) {
        Optional<Income> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new IncomeNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Income> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Income> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Income> data) {
        List<IncomeResponse> objects = new ArrayList<>();
        for (Income p : data.getContent()) {
            objects.add(new IncomeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public IncomeDto findByPropertyId(String propertyId) {
        Optional<Income> entity = repositoryQuery.findByPropertyId(propertyId);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new IncomeForPropertyNotFoundException();
    }
}
