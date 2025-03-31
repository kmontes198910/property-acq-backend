package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.MedicalStaffDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalStaffResponse implements IResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String licenseNumber;

    public MedicalStaffResponse(MedicalStaffDto dto) {
        this.id = dto.getId();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.specialty = dto.getSpecialty();
        this.licenseNumber = dto.getLicenseNumber();
    }

}
