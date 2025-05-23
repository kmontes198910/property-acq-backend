package com.kynsof.patients.application.command.patients.update;

import com.kynsof.patients.application.command.patients.create.request.CreatePatientContactInfoRequest;
import com.kynsof.patients.domain.dto.enumTye.BloodType;
import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.infrastructure.entity.IdentificationType;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePatientsCommand implements ICommand {

    private UUID id;
    private IdentificationType identificationType;
    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private String photo;
    private CreatePatientContactInfoRequest createContactInfoRequest;
    private String profession;    
    private String educationalLevel;
    private BloodType bloodType;
    private DisabilityType disabilityType;
    private int gestationTime;
    private String skinColor;

    public UpdatePatientsCommand(UUID id,IdentificationType identificationType, String identification, String name, String lastName, GenderType gender, String photo,
                                 CreatePatientContactInfoRequest createContactInfoRequest, String profession,
                                 String educationalLevel, BloodType bloodType, DisabilityType disabilityType, int gestationTime, String skinColor) {
        this.identificationType = identificationType;
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.id = id;
        this.photo = photo;
        this.createContactInfoRequest = createContactInfoRequest;
        this.profession = profession;
        this.educationalLevel = educationalLevel;
        this.bloodType = bloodType;
        this.disabilityType = disabilityType;
        this.gestationTime = gestationTime;
        this.skinColor = skinColor;
    }

    public static UpdatePatientsCommand fromRequest(UUID id, UpdatePatientsRequest request) {
        return new UpdatePatientsCommand(id,
                request.getIdentificationType(),
                request.getIdentification(),
                request.getName(),
                request.getLastName(),
                request.getGender(),
                request.getImage(),
                request.getContactInfo(),
                request.getProfession(),
                request.getEducationalLevel(),
                request.getBloodType(),
                request.getDisabilityType(),
                request.getGestationTime(),
                request.getSkinColor());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePatientMessage();
    }
}
