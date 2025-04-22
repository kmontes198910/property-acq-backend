package com.kynsoft.notification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePathCountDto implements Serializable {
    private String path;
    private Long count;
    private Double totalSizeMb;
}