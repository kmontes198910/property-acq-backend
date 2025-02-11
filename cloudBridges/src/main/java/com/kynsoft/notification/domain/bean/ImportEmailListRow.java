package com.kynsoft.notification.domain.bean;

import com.kynsof.share.core.application.excel.annotation.Cell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportEmailListRow {
   private String importProcessId;
   @Cell(position = -1)
   private int rowNumber;
   @Cell(position = 0)
   private String email;
   @Cell(position = 1)
   private String clientName;
   @Cell(position = 2)
   private String clientLastname;

}
