package com.kynsoft.notification.infrastructure.entity;

import com.kynsoft.notification.domain.dto.EmailListDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.generator.EventType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "emaillist")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@EntityListeners(AuditingEntityListener.class)
public class EmailList {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(columnDefinition = "serial", name = "email_list_id")
    @Generated(event = EventType.INSERT)
    private Long emailListId;
    private String email;
    private String clientName;
    private String clientLastname;
    @ManyToOne
    private Campaign campaign;

    @CreatedDate
    private Instant createDate;
    @LastModifiedDate
    private Instant updateDate;


    public EmailListDto toAggregate(){
        return EmailListDto.builder()
                .id(id)
                .emailListId(emailListId)
                .email(email)
                .clientName(clientName)
                .clientLastname(clientLastname)
               // .campaign(Objects.nonNull(campaign)?campaign.toAggregate():null)
                .build();
    }
}
