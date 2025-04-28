package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "position")
    private String position;

    @Column(name = "department")
    private String department;

    @Column(name = "employee_number", nullable = false, unique = true)
    private String employeeNumber;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    public Employee(UUID id, String firstName, String lastName, String email, String employeeNumber, Business business) {
        this.id = id != null ? id : UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employeeNumber = employeeNumber;
        this.business = business;
        this.active = true;
    }
    
    public Employee(EmployeeDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.hireDate = dto.getHireDate();
        this.position = dto.getPosition();
        this.department = dto.getDepartment();
        this.employeeNumber = dto.getEmployeeNumber();
        this.salary = dto.getSalary();
        this.active = dto.getActive() != null ? dto.getActive() : true;
        this.business = dto.getBusiness() != null ? new Business(dto.getBusiness()) : null;
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public EmployeeDto toAggregate() {
        return EmployeeDto.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .hireDate(this.hireDate)
                .position(this.position)
                .department(this.department)
                .employeeNumber(this.employeeNumber)
                .salary(this.salary)
                .active(this.active)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }

    public EmployeeDto toAggregateSimple() {
        return EmployeeDto.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .hireDate(this.hireDate)
                .position(this.position)
                .department(this.department)
                .employeeNumber(this.employeeNumber)
                .salary(this.salary)
                .active(this.active)
                .business(this.business != null ? 
                        BusinessDto.builder()
                            .id(this.business.getId())
                            .name(this.business.getName())
                            .build() : null)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}