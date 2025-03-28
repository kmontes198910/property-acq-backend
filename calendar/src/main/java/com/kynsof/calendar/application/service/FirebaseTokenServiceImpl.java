package com.kynsof.calendar.application.service;

import com.kynsof.calendar.application.query.firebaseToken.search.FirebaseTokenResponse;
import com.kynsof.calendar.domain.dto.FirebaseTokenDto;
import com.kynsof.calendar.domain.service.IFirebaseTokenService;
import com.kynsof.calendar.infrastructure.entity.FirebaseToken;
import com.kynsof.calendar.infrastructure.repository.command.FirebaseTokenWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.FirebaseTokenReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FirebaseTokenServiceImpl implements IFirebaseTokenService {

    private final FirebaseTokenWriteDataJPARepository commandRepository;
    private final FirebaseTokenReadDataJPARepository queryRepository;

    @Autowired
    public FirebaseTokenServiceImpl(FirebaseTokenWriteDataJPARepository commandRepository, FirebaseTokenReadDataJPARepository queryRepository) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    @Transactional
    public void create(FirebaseTokenDto object) {
        commandRepository.save(new FirebaseToken(object));
    }

    @Override
    @Transactional
    public void update(FirebaseTokenDto object) {
        var update = new FirebaseToken(object);
        commandRepository.save(update);
    }

    @Override
    @Transactional
    public void delete(FirebaseTokenDto delete) {
        var firebaseToken = new FirebaseToken(delete);
        commandRepository.delete(firebaseToken);
    }


    @Override
    public FirebaseTokenDto findById(UUID id) {
        return queryRepository.findById(id)
                .map(FirebaseToken::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.MODULE_NOT_FOUND, new ErrorField("id", "FirebaseToken not found."))));
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
     //   filterCriteria(filterCriteria);
        var specifications = new GenericSpecificationsBuilder<FirebaseTokenResponse>(filterCriteria);
        var data = queryRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<FirebaseToken> data) {
        var moduleListResponses = data.getContent().stream()
                .map(module -> new FirebaseTokenResponse(module.toAggregate()))
                .toList();
        return new PaginatedResponse(moduleListResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}