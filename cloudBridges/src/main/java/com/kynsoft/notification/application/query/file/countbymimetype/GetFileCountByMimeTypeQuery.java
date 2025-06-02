package com.kynsoft.notification.application.query.file.countbymimetype;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetFileCountByMimeTypeQuery implements IQuery{
    private UUID businessId;
}