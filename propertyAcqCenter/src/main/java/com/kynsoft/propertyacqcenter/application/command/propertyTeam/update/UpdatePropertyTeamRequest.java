package com.kynsoft.propertyacqcenter.application.command.propertyTeam.update;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePropertyTeamRequest {

    private String property;
    private UUID contact;
    private String perfil;
}
