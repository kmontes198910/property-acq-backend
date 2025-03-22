package com.kynsoft.rrhh.application.command.replicate.object;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateReplicateRequest {
    private List<ObjectEnum> objects;
}
