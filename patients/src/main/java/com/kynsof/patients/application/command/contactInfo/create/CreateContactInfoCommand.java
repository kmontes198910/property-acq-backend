package com.kynsof.patients.application.command.contactInfo.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateContactInfoCommand implements ICommand {

    private UUID id;
    private UUID patientId;
    private String telephone;
    private String address;
    private LocalDate birthdayDate;
    private UUID province;
    private UUID canton;
    private UUID parroquia;
    private String conventionalTelephone;
    private String maritalStatus;

    public CreateContactInfoCommand(UUID patientId, String telephone, String address, LocalDate localDate,
            UUID province, UUID canton, UUID parroquia, String conventionalTelephone, String maritalStatus) {
        this.patientId = patientId;
        this.telephone = telephone;
        this.address = address;
        this.birthdayDate = localDate;
        this.province = province;
        this.canton = canton;
        this.parroquia = parroquia;
        this.conventionalTelephone = conventionalTelephone;
        this.maritalStatus = maritalStatus;
    }

    public static CreateContactInfoCommand fromRequest(UUID patientId, CreateContactInfoRequest request) {
        return new CreateContactInfoCommand(patientId, request.getTelephone(),
                request.getAddress(), request.getBirthdayDate(), request.getProvince(),
                request.getCanton(), request.getParroquia(), 
                request.getConventionalTelephone(), request.getMaritalStatus());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateContactInfoMessage(id);
    }
}
