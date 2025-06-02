package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.ListingAgentDto;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingAgent {

    private String agentName;
    private String agentPhone;
    private String agentEmail;
    private String agentWebsite;

    public ListingAgent(ListingAgentDto dto) {
        this.agentName = dto.getName();
        this.agentPhone = dto.getPhone();
        this.agentEmail = dto.getEmail();
        this.agentWebsite = dto.getWebsite();
    }

    public ListingAgentDto toAggregate() {
        return ListingAgentDto.builder()
                .name(agentName)
                .phone(agentPhone)
                .email(agentEmail)
                .website(agentWebsite)
                .build();
    }
}
