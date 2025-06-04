package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.patientVaccine.create.CreatePatientVaccineCommand;
import com.kynsof.treatments.application.command.patientVaccine.create.CreatePatientVaccineMessage;
import com.kynsof.treatments.application.command.patientVaccine.create.CreatePatientVaccineRequest;
import com.kynsof.treatments.application.command.patientVaccine.delete.PatientPatientVaccineMessage;
import com.kynsof.treatments.application.command.patientVaccine.delete.PatientsPatientVaccineCommand;
import com.kynsof.treatments.application.command.patientVaccine.update.UpdatePatientVaccineCommand;
import com.kynsof.treatments.application.command.patientVaccine.update.UpdatePatientVaccineMessage;
import com.kynsof.treatments.application.command.patientVaccine.update.UpdatePatientVaccineRequest;
import com.kynsof.treatments.application.command.vaccine.delete.VaccineDeleteCommand;
import com.kynsof.treatments.application.command.vaccine.delete.VaccineDeleteMessage;
import com.kynsof.treatments.application.query.patientVaccine.getById.FindByIdPatientVaccineQuery;
import com.kynsof.treatments.application.query.patientVaccine.getall.PatientVaccineResponse;
import com.kynsof.treatments.application.query.patientVaccine.search.GetSearchPatientsVaccineQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patient-vaccine")
public class PatientVaccineController {

    private final IMediator mediator;

    public PatientVaccineController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreatePatientVaccineMessage> create(@RequestBody CreatePatientVaccineRequest request)  {
        CreatePatientVaccineCommand createCommand = CreatePatientVaccineCommand.fromRequest(request);
        CreatePatientVaccineMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }


//    @GetMapping("/all")
//    public ResponseEntity<PaginatedResponse> getAll(@RequestParam(defaultValue = "20") Integer pageSize,
//                                                    @RequestParam(defaultValue = "0") Integer page,
//                                                    @RequestParam(defaultValue = "") UUID patientId)
//    {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        GetAllPatientVaccineQuery query = new GetAllPatientVaccineQuery(pageable,patientId);
//        PaginatedResponse response = mediator.send(query);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPatientsVaccineQuery query = new GetSearchPatientsVaccineQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PatientVaccineResponse> getById(@PathVariable UUID id) {

        FindByIdPatientVaccineQuery query = new FindByIdPatientVaccineQuery(id);
        PatientVaccineResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UpdatePatientVaccineMessage> update(@PathVariable UUID id, @RequestBody UpdatePatientVaccineRequest request) {
        UpdatePatientVaccineCommand command = UpdatePatientVaccineCommand.fromRequest(id,request );
        UpdatePatientVaccineMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        PatientsPatientVaccineCommand query = new PatientsPatientVaccineCommand(id);
        PatientPatientVaccineMessage response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

}