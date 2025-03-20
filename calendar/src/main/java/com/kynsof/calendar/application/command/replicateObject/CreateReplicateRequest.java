package com.kynsof.calendar.application.command.replicateObject;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateReplicateRequest {
    private List<ObjectEnum> objects;
}
