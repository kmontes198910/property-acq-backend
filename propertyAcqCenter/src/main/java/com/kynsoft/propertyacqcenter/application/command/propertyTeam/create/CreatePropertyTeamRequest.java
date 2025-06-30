package com.kynsoft.propertyacqcenter.application.command.propertyTeam.create;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePropertyTeamRequest {

    private String property;
    private UUID contact;
    private String perfil;
}
