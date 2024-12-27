package com.kynsof.patients.controller;


import com.kynsof.patients.application.command.patientInsurance.create.CreatePatientInsuranceCommand;
import com.kynsof.patients.application.command.patientInsurance.create.CreatePatientInsuranceMessage;
import com.kynsof.patients.application.command.patientInsurance.create.CreatePatientInsuranceRequest;
import com.kynsof.patients.application.command.patientInsurance.delete.DeletePatientInsuranceCommand;
import com.kynsof.patients.application.command.patientInsurance.delete.DeletePatientInsuranceMessage;
import com.kynsof.patients.application.command.patientInsurance.update.UpdatePatientInsuranceCommand;
import com.kynsof.patients.application.command.patientInsurance.update.UpdatePatientInsuranceMessage;
import com.kynsof.patients.application.command.patientInsurance.update.UpdatePatientInsuranceRequest;
import com.kynsof.patients.application.command.patients.delete.DeletePatientsCommand;
import com.kynsof.patients.application.command.patients.delete.PatientDeleteMessage;
import com.kynsof.patients.application.query.patients.search.GetSearchPatientsQuery;
import com.kynsof.patients.application.query.patientsInsurance.getById.FindPatientsInsuranceByIdQuery;
import com.kynsof.patients.application.query.patientsInsurance.getById.PatientsInsuranceByIdResponse;
import com.kynsof.patients.application.query.patientsInsurance.search.GetSearchPatientInsuranceQuery;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patient-insurance")
public class PatientInsuranceController {

    private final IMediator mediator;

    public PatientInsuranceController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> createPatientInsurance(@RequestBody CreatePatientInsuranceRequest request)  {
        CreatePatientInsuranceCommand createCommand = CreatePatientInsuranceCommand.fromRequest(request);
        CreatePatientInsuranceMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }



    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getByIdPatientInsurance(@PathVariable UUID id) {

        FindPatientsInsuranceByIdQuery query = new FindPatientsInsuranceByIdQuery(id);
        PatientsInsuranceByIdResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> updatePatientInsurance(@PathVariable UUID id, @RequestBody UpdatePatientInsuranceRequest request) {

        UpdatePatientInsuranceCommand command = UpdatePatientInsuranceCommand.fromRequest(id,request );
        UpdatePatientInsuranceMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPatientInsuranceQuery query = new GetSearchPatientInsuranceQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {

        DeletePatientInsuranceCommand command = new DeletePatientInsuranceCommand(id);
        DeletePatientInsuranceMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }
}