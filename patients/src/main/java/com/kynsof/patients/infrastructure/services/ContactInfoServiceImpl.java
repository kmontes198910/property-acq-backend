package com.kynsof.patients.infrastructure.services;

import com.kynsof.patients.application.query.contactInfo.getall.ContactInfoResponse;
import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.infrastructure.entity.ContactInformation;
import com.kynsof.patients.infrastructure.repository.command.ContactInfoWriteDataJPARepository;
import com.kynsof.patients.infrastructure.repository.query.ContactInfoReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactInfoServiceImpl implements IContactInfoService {

    private final ContactInfoWriteDataJPARepository repositoryCommand;
    private final ContactInfoReadDataJPARepository repositoryQuery;

    public ContactInfoServiceImpl(ContactInfoWriteDataJPARepository repositoryCommand, ContactInfoReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public UUID create(ContactInfoDto dto) {
        ContactInformation contactInformation = new ContactInformation(dto);
        this.repositoryCommand.save(contactInformation);
        return dto.getId();
    }

    @Override
    public UUID update(ContactInfoDto contactInfoDto) {
        if (contactInfoDto == null || contactInfoDto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
        }

//        this.repositoryQuery.findById(contactInfoDto.getId())
//                .map(contactInformation -> {
//                    if (contactInfoDto.getAddress() != null) contactInformation.setAddress(contactInfoDto.getAddress());
//                    if (contactInfoDto.getTelephone() != null) contactInformation.setTelephone(contactInfoDto.getTelephone());
//                    if (contactInfoDto.getBirthdayDate() != null) contactInformation.setBirthdayDate(contactInfoDto.getBirthdayDate());
//                    if (contactInfoDto.getEmail() != null) contactInformation.setEmail(contactInfoDto.getEmail());
//                    if (contactInfoDto.getGeographicLocation() != null)
//                        contactInformation.setGeographicLocation(new GeographicLocation(contactInfoDto.getGeographicLocation()));
//                })
//                .orElseThrow(() -> new EntityNotFoundException("ContactInfo with ID " + contactInfoDto.getId() + " not found"));

        ContactInformation update = new ContactInformation(contactInfoDto);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
        return contactInfoDto.getId();
    }


    @Override
    public ContactInfoDto findById(UUID id) {
        Optional<ContactInformation> contactInformation = this.repositoryQuery.findById(id);
        if (contactInformation.isPresent()) {
            return contactInformation.get().toAggregate();
        }
        throw new RuntimeException("ContactInfo not found.");
    }

    @Override
    public PaginatedResponse findAll(Pageable pageable) {
        Page<ContactInformation> data = this.repositoryQuery.findAll( pageable);

        return getPaginatedResponse(data);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<ContactInformation> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<ContactInformation> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    @Override
    public ContactInfoDto findByPatientId(UUID patientId) {
        Optional<ContactInformation> contactInformation = this.repositoryQuery.findByPatientId(patientId);
        if (contactInformation.isPresent()) {
            return contactInformation.get().toAggregate();
        }
      return new ContactInfoDto();
    }

    public ContactInfoDto findByIdPatient(UUID patientId) {
        Optional<ContactInformation> contactInformation = this.repositoryQuery.findByPatientId(patientId);
        if (contactInformation.isPresent()) {
            return contactInformation.get().toAggregate();
        }
      throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.CONTACT_INFO_NOT_FOUND, new ErrorField("id", "Contact info not found.")));
    }

    private PaginatedResponse getPaginatedResponse(Page<ContactInformation> data) {
        List<ContactInfoResponse> patients = new ArrayList<>();
        for (ContactInformation p : data.getContent()) {
            patients.add(new ContactInfoResponse(p.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

}
