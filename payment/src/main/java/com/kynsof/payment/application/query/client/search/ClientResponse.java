package com.kynsof.payment.application.query.client.search;

import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.payment.domain.dto.enumDto.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class ClientResponse implements IResponse {
    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private Status status;

    public ClientResponse(ClientDto dto) {
        this.id = dto.getId();
        this.identification = dto.getIdentification();
        this.name = dto.getName();
        this.lastName = dto.getLastName();
        this.status = dto.getStatus();
    }

}
