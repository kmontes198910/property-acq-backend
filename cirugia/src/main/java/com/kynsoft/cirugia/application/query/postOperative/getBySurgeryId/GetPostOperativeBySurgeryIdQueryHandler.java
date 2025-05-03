package com.kynsoft.cirugia.application.query.postOperative.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.PostOperative;
import com.kynsoft.cirugia.domain.service.IPostOperativeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetPostOperativeBySurgeryIdQueryHandler implements IQueryHandler<GetPostOperativeBySurgeryIdQuery, PostOperativeResponse> {
    private final IPostOperativeRepository postOperativeRepository;

    public GetPostOperativeBySurgeryIdQueryHandler(IPostOperativeRepository postOperativeRepository) {
        this.postOperativeRepository = postOperativeRepository;
    }

    @Override
    public PostOperativeResponse handle(GetPostOperativeBySurgeryIdQuery query) {
        Optional<PostOperative> postOperative = postOperativeRepository.findBySurgeryId(query.getSurgeryId().toString());
        if (postOperative.isEmpty()) {
            throw new RuntimeException("PostOperative not found with surgeryId: " + query.getSurgeryId());
        }
        return new PostOperativeResponse(postOperative.get());
    }
}