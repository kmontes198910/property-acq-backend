package com.kynsof.payment.application.command.client.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientRequest {

    private String identification;
    private String name;
    private String lastName;
}
