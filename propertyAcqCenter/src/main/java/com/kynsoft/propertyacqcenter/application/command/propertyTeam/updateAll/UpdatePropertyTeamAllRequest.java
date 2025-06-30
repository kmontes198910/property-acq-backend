package com.kynsoft.propertyacqcenter.application.command.propertyTeam.updateAll;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePropertyTeamAllRequest {

    private UUID buyerEntityName;
    private String property;
}
