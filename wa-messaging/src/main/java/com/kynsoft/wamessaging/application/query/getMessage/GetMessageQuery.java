package com.kynsoft.wamessaging.application.query.getMessage;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class GetMessageQuery implements IQuery {
    private final UUID id;

}
