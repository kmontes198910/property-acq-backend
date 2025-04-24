package com.kynsoft.propertyacqcenter.application.query.contactPerson.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.ContactPersonResponse;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdContactPersonQueryHandler implements IQueryHandler<GetByIdContactPersonQuery, ContactPersonResponse> {

    private final IContactPersonService contactPersonService;

    public GetByIdContactPersonQueryHandler(IContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public ContactPersonResponse handle(GetByIdContactPersonQuery query) {
        return new ContactPersonResponse(this.contactPersonService.findById(query.getId()));
    }
}
