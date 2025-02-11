package com.kynsoft.notification.application.query.emaillist.importError;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class ImportEmailListErrorRequest {
    private final String query;
    private final Pageable pageable;


    public ImportEmailListErrorRequest(String query, Pageable pageable) {
        this.query = query;
        this.pageable = pageable;
    }
}
