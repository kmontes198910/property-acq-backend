package com.kynsoft.rrhh.controller;


import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.rrhh.application.command.doctor.create.CreateDoctorCommand;
import com.kynsoft.rrhh.application.command.doctor.create.CreateDoctorMessage;
import com.kynsoft.rrhh.application.command.doctor.create.CreateDoctorRequest;
import com.kynsoft.rrhh.application.command.doctor.delete.DeleteDoctorCommand;
import com.kynsoft.rrhh.application.command.doctor.delete.DeleteDoctorMessage;
import com.kynsoft.rrhh.application.command.doctor.update.UpdateDoctorCommand;
import com.kynsoft.rrhh.application.command.doctor.update.UpdateDoctorMessage;
import com.kynsoft.rrhh.application.command.doctor.update.UpdateDoctorRequest;
import com.kynsoft.rrhh.application.query.doctor.getByIdHttpReplicate.FindHttpDoctorByIdQuery;
import com.kynsoft.rrhh.application.query.doctor.getbyid.DoctorResponse;
import com.kynsoft.rrhh.application.query.doctor.getbyid.FindByIdDoctorQuery;
import com.kynsoft.rrhh.application.query.doctor.search.GetSearchDoctorQuery;
import com.kynsoft.rrhh.application.query.users.getByIdentification.FindByIdentificationUserSystemsQuery;
import com.kynsoft.rrhh.application.query.users.getByIdentification.UserSystemsByIdentificationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final IMediator mediator;

    public DoctorController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreateDoctorRequest request)  {
        CreateDoctorCommand createCommand = CreateDoctorCommand.fromRequest(request);
        CreateDoctorMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdDoctorQuery query = new FindByIdDoctorQuery(id);
        DoctorResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/http/replicate/{id}")
    public ResponseEntity<?> getByIdHttpReplicate(@PathVariable UUID id) {

        FindHttpDoctorByIdQuery query = new FindHttpDoctorByIdQuery(id);
        DoctorHttp response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/identification/{identification}")
    public ResponseEntity<?> getByIdentification(@PathVariable String identification) {

        FindByIdentificationUserSystemsQuery query = new FindByIdentificationUserSystemsQuery(identification);
        UserSystemsByIdentificationResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchDoctorQuery query = new GetSearchDoctorQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {

        DeleteDoctorCommand command = new DeleteDoctorCommand(id);
        DeleteDoctorMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id,@RequestBody UpdateDoctorRequest request) {

        UpdateDoctorCommand command = UpdateDoctorCommand.fromRequest(request,id);
        UpdateDoctorMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
