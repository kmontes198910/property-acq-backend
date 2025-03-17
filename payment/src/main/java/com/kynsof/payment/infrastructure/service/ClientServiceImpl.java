package com.kynsof.payment.infrastructure.service;

import com.kynsof.payment.application.query.client.search.ClientResponse;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.payment.infrastructure.entity.Client;
import com.kynsof.payment.infrastructure.repositories.command.ClientWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.ClientReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientWriteDataJPARepository repositoryCommand;

    @Autowired
    private ClientReadDataJPARepository repositoryQuery;

    @Override
    public UUID create(ClientDto dto) {
        Client entity = this.repositoryCommand.save(new Client(dto));
        return entity.getId();
    }

    @Override
    public UUID update(ClientDto doctorDto) {
        if (doctorDto == null || doctorDto.getId() == null) {
            throw new IllegalArgumentException("Doctor DTO or ID cannot be null");
        }

        this.repositoryQuery.findById(doctorDto.getId())
                .map(patient -> {
                    if (doctorDto.getName() != null) {
                        patient.setName(doctorDto.getName());
                    }
                    if (doctorDto.getStatus() != null) {
                        patient.setStatus(doctorDto.getStatus());
                    }

                    return this.repositoryCommand.save(patient);
                })
                .orElseThrow(() -> new EntityNotFoundException("Doctor with ID " + doctorDto.getId() + " not found"));

        return doctorDto.getId();
    }

    @Override
    public ClientDto findById(UUID id) {
        Optional<Client> patient = this.repositoryQuery.findById(id);
        if (patient.isPresent()) {
            return patient.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.DOCTOR_NOT_FOUND, new ErrorField("id", "Doctor not found.")));
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Client> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Client> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Client> data) {
        List<ClientResponse> patients = new ArrayList<>();
        for (Client o : data.getContent()) {
            patients.add(new ClientResponse(o.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
