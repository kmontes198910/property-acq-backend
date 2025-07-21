package com.kynsof.identity.application.command.email.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEmailRequest {

    private String email;
    private String name;
    private String lastName;
    private String pass;
}
