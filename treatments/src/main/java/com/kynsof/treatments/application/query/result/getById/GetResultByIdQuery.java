package com.kynsof.treatments.application.query.result.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetResultByIdQuery implements IQuery{
    private final UUID id;
}