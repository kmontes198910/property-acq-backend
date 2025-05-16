package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IEmployeeService {
    // Method to create a new employee
    UUID create(EmployeeDto employee);

    // Method to update an existing employee
    void update(EmployeeDto employee);

    // Method to delete an employee by ID
    void delete(UUID id);

    // Method to find an employee by ID
    EmployeeDto findById(UUID id);

    // Method to find an employee by email
    EmployeeDto findByEmail(String email);

    // Method to search for employees with pagination and filter criteria
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    // Method to check if an employee exists by email
    Long countByEmailAndNotId(String email, UUID id);

    long countByEmail(String email);
}
