package com.kynsoft.notification.domain.dto;

import com.kynsoft.notification.domain.dtoEnum.CampaignStatus;
import com.kynsoft.notification.infrastructure.entity.Campaign;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * DTO for {@link com.kynsoft.notification.infrastructure.entity.Campaign}
 */

@Builder
@Data
public class CampaignDto implements Serializable {
    @NotNull
    private UUID id;
    private UUID ownerId;
    private Long campaignId;
    private String code;
    private LocalDate campaignDate;
    private CampaignStatus status;
    private long amountEmailSent;
    private long amountEmailOpen;
    private TemplateDto template;
   private  TenantDto tenant;
    private Set<EmailListDto> emailList;
    private String subject;


    public Campaign toAggregate(){
        return Campaign.builder()
                .id(id)
                .campaignId(campaignId)
                .code(code)
                .campaignDate(campaignDate)
                .status(status)
                .amountEmailSent(amountEmailSent)
                .amountEmailOpen(amountEmailOpen)
                .template(Objects.nonNull(template)?template.toAggregate():null)
                .tenant(Objects.nonNull(tenant)?tenant.toAggregate():null)
                .emailList(Objects.nonNull(emailList)?emailList.stream().map(EmailListDto::toAggregate).collect(Collectors.toSet()):null)
                .ownerId(ownerId)
                .subject(subject)
                .build();

    }

}