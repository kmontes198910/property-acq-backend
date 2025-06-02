package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.EmployeeResponse;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.EmployeeNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.employee.EmployeeEmailFormatException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.employee.EmployeeEmailMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Employee;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.EmployeeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.EmployeeReadDataJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeWriteDataJPARepository repositoryCommand;

    private final EmployeeReadDataJPARepository repositoryQuery;

    public EmployeeServiceImpl(EmployeeWriteDataJPARepository repositoryCommand, EmployeeReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(EmployeeDto employee) {
        return this.repositoryCommand.save(new Employee(employee)).getId();
    }

    @Override
    public void update(EmployeeDto employee) {
        Employee entity = new Employee(employee);
        entity.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(entity);
    }

    @Override
    public void delete(UUID id) {
        EmployeeDto employee = this.findById(id);
        this.repositoryCommand.deleteById(employee.getId());
    }

    @Override
    public EmployeeDto findById(UUID id) {
        Optional<Employee> entity = repositoryQuery.findById(id);
        if(entity.isPresent()) {
            return entity.get().toAggregateSimple();
        } else {
            throw new EmployeeNotFoundException(id.toString(), "ID");
        }
    }

    @Override
    public EmployeeDto findByEmail(String email) {
        Optional<Employee> entity = repositoryQuery.findByEmail(email);
        if(entity.isPresent()) {
            return entity.get().toAggregate();
        } else {
            throw new EmployeeNotFoundException(email, "email");
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Employee> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Employee> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    @Override
    public Long countByEmailAndNotId(String email, UUID id) {
        Long cont = this.repositoryQuery.countByEmailAndNotId(email, id);
        return cont == null ? 0 : cont;
    }

    private PaginatedResponse getPaginatedResponse(Page<Employee> data) {
        List<EmployeeResponse> objects = new ArrayList<>();
        for (Employee p : data.getContent()) {
            objects.add(new EmployeeResponse(p.toAggregate()));
        }

        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public long countByEmail(String email) {
        return this.repositoryQuery.countByEmail(email);
    }

    @Override
    public void validateEmail(String email, UUID id) {
        if (this.countByEmailAndNotId(email, id) > 0) {
            throw new EmployeeEmailMustBeUniqueException(email);
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            throw new EmployeeEmailFormatException(email);
        }
    }

}
