package com.kynsof.patients.controller;

import com.kynsof.patients.application.command.patients.create.CreatePatientMessage;
import com.kynsof.patients.application.command.patients.create.CreatePatientsCommand;
import com.kynsof.patients.application.command.patients.create.request.CreatePatientsRequest;
import com.kynsof.patients.application.command.patients.createInsurance.CreateInsuranceCommand;
import com.kynsof.patients.application.command.patients.createInsurance.CreateInsuranceMessage;
import com.kynsof.patients.application.command.patients.createInsurance.CreateInsuranceRequest;
import com.kynsof.patients.application.command.patients.delete.DeletePatientsCommand;
import com.kynsof.patients.application.command.patients.delete.PatientDeleteMessage;
import com.kynsof.patients.application.command.patients.patientsKeyCloack.patientsKeyCloakCommand;
import com.kynsof.patients.application.command.patients.patientsKeyCloack.patientsKeyCloakMessage;
import com.kynsof.patients.application.command.patients.update.UpdatePatientMessage;
import com.kynsof.patients.application.command.patients.update.UpdatePatientsCommand;
import com.kynsof.patients.application.command.patients.update.UpdatePatientsRequest;
import com.kynsof.patients.application.query.patients.getById.FindPatientsByIdQuery;
import com.kynsof.patients.application.query.patients.getById.PatientByIdResponse;
import com.kynsof.patients.application.query.patients.getByIdHttpReplicate.FindHttpPatientsByIdQuery;
import com.kynsof.patients.application.query.patients.getByIdentification.FindPatientsByIdentificationQuery;
import com.kynsof.patients.application.query.patients.getall.GetAllPatientsFilterQuery;
import com.kynsof.patients.application.query.patients.getall.PatientsResponse;
import com.kynsof.patients.application.query.patients.keyCloak.FindPatientsByKeyCloakIdQuery;
import com.kynsof.patients.application.query.patients.keyCloak.FindPatientsByKeyCloakIdResponse;
import com.kynsof.patients.application.query.patients.search.GetSearchPatientsQuery;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.domain.response.ApiError;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@Slf4j
public class PatientsController {

    private final IMediator mediator;

    public PatientsController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreatePatientMessage> create(@RequestBody CreatePatientsRequest request) {
        CreatePatientsCommand createCommand = CreatePatientsCommand.fromRequest(request);
        CreatePatientMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

//    @PatchMapping("/admin/updated/{patientId}")
//    public ResponseEntity<?> updatePatientAdmin(@PathVariable UUID patientId, @RequestBody CreatePatientsAdminRequest request) {
//        CreatePatientAdminCommand createCommand = CreatePatientAdminCommand.fromRequest(patientId, request);
//        CreatePatientAdminMessage response = mediator.send(createCommand);
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping(path = "/admin/updated/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable UUID id) {

        FindPatientsByIdQuery query = new FindPatientsByIdQuery(id);
        PatientByIdResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/insurance")
    public ResponseEntity<?> createInsurance(@RequestBody CreateInsuranceRequest request) {
        CreateInsuranceCommand createCommand = CreateInsuranceCommand.fromRequest(request);
        CreateInsuranceMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResponse> getAll(@RequestParam(defaultValue = "20") Integer pageSize,
                                                    @RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "") UUID patientId,
                                                    @RequestParam(defaultValue = "") UUID primeId,
                                                    @RequestParam(defaultValue = "") String identification) {
        Pageable pageable = PageRequest.of(page, pageSize);
        GetAllPatientsFilterQuery query = new GetAllPatientsFilterQuery(pageable, patientId, identification, primeId);
        PaginatedResponse data = mediator.send(query);

        return ResponseEntity.ok(data);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPatientsQuery query = new GetSearchPatientsQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PatientByIdResponse> getById(@PathVariable UUID id) {

        FindPatientsByIdQuery query = new FindPatientsByIdQuery(id);
        PatientByIdResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/http/replicate/{id}")
    public ResponseEntity<PatientHttp> getByIdHttpReplicate(@PathVariable UUID id) {

        FindHttpPatientsByIdQuery query = new FindHttpPatientsByIdQuery(id);
        PatientHttp response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/identification/{identification}")
    public ResponseEntity<PatientsResponse> getByIdentification(@PathVariable String identification) {

        FindPatientsByIdentificationQuery query = new FindPatientsByIdentificationQuery(identification);
        PatientsResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/setKeycloak/{patientId}/{keyCloakId}")
    public ResponseEntity<?> setKeycloak(@PathVariable UUID patientId, @PathVariable UUID keyCloakId) {
        patientsKeyCloakCommand query = new patientsKeyCloakCommand(patientId, keyCloakId);
        patientsKeyCloakMessage response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UpdatePatientMessage> update(@PathVariable UUID id, @RequestBody UpdatePatientsRequest request) {

        UpdatePatientsCommand command = UpdatePatientsCommand.fromRequest(id, request);
        UpdatePatientMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatientDeleteMessage> deleteServices(@PathVariable("id") UUID id) {

        DeletePatientsCommand command = new DeletePatientsCommand(id);
        PatientDeleteMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
                                            @RequestHeader(value = "X-User-Roles", required = false) String userRoles) {
        
        // Priorizar el ID enviado por el gateway, si está presente
        String patientId = (userId != null && !userId.isEmpty()) ? userId : null;
        
        if (patientId == null) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(new ApiError("No se pudo identificar al usuario")));
        }

        // Registrar información adicional recibida desde el gateway (opcional, solo para depuración)
        if (userEmail != null || userRoles != null) {
            log.debug("Información adicional del usuario - Email: {}, Roles: {}", 
                      userEmail != null ? userEmail : "N/A", 
                      userRoles != null ? userRoles : "N/A");
        }

        try {
            UUID patientUuid = UUID.fromString(patientId);
            FindPatientsByKeyCloakIdQuery query = new FindPatientsByKeyCloakIdQuery(patientUuid);
            FindPatientsByKeyCloakIdResponse response = mediator.send(query);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            log.error("Error al convertir el ID del paciente a UUID: {}", patientId, e);
            return ResponseEntity.badRequest().body(ApiResponse.fail(new ApiError("El formato del ID de usuario no es válido")));
        } catch (Exception e) {
            log.error("Error al obtener información del paciente con ID: {}", patientId, e);
            return ResponseEntity.status(500).body(ApiResponse.fail(new ApiError("Error al obtener información del paciente")));
        }
    }
}
