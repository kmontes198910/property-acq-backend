package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactBasicResponse implements IResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    public ContactBasicResponse(ContactDto contactDto){
        this.id = contactDto.getId();
        this.firstName = contactDto.getFirstName();
        this.lastName = contactDto.getLastName();
        this.email = contactDto.getEmail();
    }
}
