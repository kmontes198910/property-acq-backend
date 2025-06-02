//package com.kynsof.patients.application.command.patients.updatePatientAdmin;
//
//import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
//import com.kynsof.patients.domain.dto.enumTye.GenderType;
//import com.kynsof.share.core.domain.bus.command.ICommand;
//import com.kynsof.share.core.domain.bus.command.ICommandMessage;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.UUID;
//
//@Getter
//@Setter
//public class CreatePatientAdminCommand implements ICommand {
//    private UUID id;
//    private String identification;
//    private GenderType gender;
//    private Boolean hasDisability;
//    private Boolean isPregnant;
//    private String photo;
//    private int gestationTime;
//    private DisabilityType disabilityType;
//
//    public CreatePatientAdminCommand(UUID id,String identification, GenderType gender, Boolean hasDisability, Boolean isPregnant, String photo,
//                                 int gestationTime, DisabilityType disabilityType){
//
//        this.id = id;
//        this.identification = identification;
//        this.gender = gender;
//
//        this.hasDisability = hasDisability;
//        this.isPregnant = isPregnant;
//        this.photo = photo;
//        this.gestationTime = gestationTime;
//        this.disabilityType = disabilityType;
//
//    }
//
//    public static CreatePatientAdminCommand fromRequest(UUID id,CreatePatientsAdminRequest request) {
//        return new CreatePatientAdminCommand(id, request.getIdentification(), request.getGender(),
//                request.getHasDisability(),request.getIsPregnant(),
//                request.getImage(),request.getGestationTime(), request.getDisabilityType());
//    }
//
//
//    @Override
//    public ICommandMessage getMessage() {
//        return new CreatePatientAdminMessage(id);
//    }
//}
