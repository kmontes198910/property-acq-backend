package com.kynsoft.notification.infrastructure.entity.redis;

import com.kynsoft.notification.domain.dtoEnum.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@AllArgsConstructor
@RedisHash(value = "importprocesstatus",timeToLive = 3600)
public class ImportEmailListProcessStatus {
    @Id
    private String id;
    @Indexed
    private String importProcessId;
    private ProcessStatus status;
    private boolean hasError;
    private String exceptionMessage;
}
