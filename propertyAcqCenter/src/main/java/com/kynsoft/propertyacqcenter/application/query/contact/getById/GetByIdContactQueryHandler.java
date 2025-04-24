package com.kynsoft.propertyacqcenter.application.query.contact.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.ContactResponse;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdContactQueryHandler implements IQueryHandler<GetByIdContactQuery, ContactResponse> {

    private final IContactService contactService;

    public GetByIdContactQueryHandler(IContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ContactResponse handle(GetByIdContactQuery query) {
        return new ContactResponse(this.contactService.findById(query.getId()));
    }
}
