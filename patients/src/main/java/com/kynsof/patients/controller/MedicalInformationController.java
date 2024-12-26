//package com.kynsof.patients.controller;
//
//import com.kynsof.patients.application.command.additionalInfo.delete.DeleteAdditionalInfoCommand;
//import com.kynsof.patients.application.command.additionalInfo.delete.DeleteAdditionalMessage;
//import com.kynsof.patients.application.command.medicalInformation.create.CreateMedicalInformationCommand;
//import com.kynsof.patients.application.command.medicalInformation.create.CreateMedicalInformationMessage;
//import com.kynsof.patients.application.command.medicalInformation.create.CreateMedicalInformationRequest;
//import com.kynsof.patients.application.command.medicalInformation.update.UpdateMedicalInformationCommand;
//import com.kynsof.patients.application.command.medicalInformation.update.UpdateMedicalInformationMessage;
//import com.kynsof.patients.application.command.medicalInformation.update.UpdateMedicalInformationRequest;
//import com.kynsof.patients.application.query.medicalInformation.getById.FindByIdMedicalInformationQuery;
//import com.kynsof.patients.application.query.medicalInformation.getall.GetAllMedicalInformationQuery;
//import com.kynsof.patients.application.query.medicalInformation.getall.MedicalInformationResponse;
//import com.kynsof.patients.application.query.medicalInformation.getbypatient.FindMedicalInformationByIdPatientQuery;
//import com.kynsof.share.core.domain.response.PaginatedResponse;
//import com.kynsof.share.core.infrastructure.bus.IMediator;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/medical-information")
//public class MedicalInformationController {
//
//    private final IMediator mediator;
//
//    public MedicalInformationController(IMediator mediator){
//
//        this.mediator = mediator;
//    }
//
//    @PatchMapping("/create/{patientId}")
//    public ResponseEntity<CreateMedicalInformationMessage> create(@PathVariable UUID patientId,@RequestBody CreateMedicalInformationRequest request)  {
//        CreateMedicalInformationCommand createCommand = CreateMedicalInformationCommand.fromRequest(patientId,request);
//        CreateMedicalInformationMessage response = mediator.send(createCommand);
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<PaginatedResponse> getAll(@RequestParam(defaultValue = "20") Integer pageSize,
//                                                    @RequestParam(defaultValue = "0") Integer page,
//                                                    @RequestParam(defaultValue = "") UUID idPatients,
//                                                    @RequestParam(defaultValue = "") String emergencyContactName)
//    {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        GetAllMedicalInformationQuery query = new GetAllMedicalInformationQuery(pageable, idPatients, emergencyContactName);
//        PaginatedResponse response = mediator.send(query);
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping(path = "/{id}")
//    public ResponseEntity<MedicalInformationResponse> getById(@PathVariable UUID id) {
//
//        FindByIdMedicalInformationQuery query = new FindByIdMedicalInformationQuery(id);
//        MedicalInformationResponse response = mediator.send(query);
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping(path = "/patient/{id}")
//    public ResponseEntity<MedicalInformationResponse> getByIdPatient(@PathVariable UUID id) {
//
//        FindMedicalInformationByIdPatientQuery query = new FindMedicalInformationByIdPatientQuery(id);
//        MedicalInformationResponse response = mediator.send(query);
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PatchMapping(path = "/{id}")
//    public ResponseEntity<UpdateMedicalInformationMessage> update(@PathVariable UUID id, @RequestBody UpdateMedicalInformationRequest request) {
//
//        UpdateMedicalInformationCommand command = UpdateMedicalInformationCommand.fromRequest(id,request );
//        UpdateMedicalInformationMessage response = mediator.send(command);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<DeleteAdditionalMessage> deleteServices(@PathVariable("id") UUID id) {
//
//        DeleteAdditionalInfoCommand command = new DeleteAdditionalInfoCommand(id);
//        DeleteAdditionalMessage response = mediator.send(command);
//
//        return ResponseEntity.ok(response);
//    }
//
//}