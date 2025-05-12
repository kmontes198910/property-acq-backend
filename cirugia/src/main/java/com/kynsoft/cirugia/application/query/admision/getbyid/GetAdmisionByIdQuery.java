package com.kynsoft.cirugia.application.query.admision.getbyid;


import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Query para obtener una admisión por su ID
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdmisionByIdQuery implements IQuery {
    private UUID id;
}
