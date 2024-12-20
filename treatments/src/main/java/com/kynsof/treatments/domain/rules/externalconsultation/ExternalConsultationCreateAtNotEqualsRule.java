package com.kynsof.treatments.domain.rules.externalconsultation;

import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;
import com.kynsof.share.utils.ConfigureTimeZone;

import java.time.LocalDateTime;
import java.util.Date;

public class ExternalConsultationCreateAtNotEqualsRule extends BusinessRule {

    private final LocalDateTime date;

    public ExternalConsultationCreateAtNotEqualsRule(Date consultationTime) {
        super(
                DomainErrorMessage.CONSULT_EXTERN_DATE_EXP,
                new ErrorField("date", "La consulta externa no se puede modificar. La fecha de creación ya pasó..")
        );
        this.date = ConfigureTimeZone.convertDateToLocalDateTime(consultationTime);
    }

    @Override
    public boolean isBroken() {
        return ConfigureTimeZone.validateEqualsDate(date);
    }
}
