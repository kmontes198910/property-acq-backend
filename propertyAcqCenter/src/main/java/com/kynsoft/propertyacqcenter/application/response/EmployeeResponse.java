package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeResponse implements IResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String position;
    private String department;
    private Double salary;
    private Boolean active;
    private BusinessResponse business;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private List<ManageRoleResponse> roles;

    public EmployeeResponse(EmployeeDto dto){
        this.id = dto.getId();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.hireDate = dto.getHireDate();
        this.position = dto.getPosition();
        this.department = dto.getDepartment();
        this.salary = dto.getSalary();
        this.active = dto.getActive();
        this.business = dto.getBusiness() != null ? new BusinessResponse(dto.getBusiness()) : null;
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.roles = get(dto.getRoles());
    }

    private List<ManageRoleResponse> get(List<ManageRolDto> ids) {
        return ids.stream()
                .map(ManageRoleResponse::new)
                .collect(Collectors.toList());
    }

}
