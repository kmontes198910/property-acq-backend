package com.kynsof.payment.controller;

import com.kynsof.payment.domain.dto.excel.BillingExcelErrors;
import com.kynsof.payment.domain.dto.excel.exception.BusinessNotFoundException;
import com.kynsof.payment.domain.dto.excel.processStatus.ProcessStatus;
import com.kynsof.payment.domain.dto.excel.processStatus.ProcessStatusEnum;
import com.kynsof.payment.infrastructure.service.readExcel.ReadErrorsBillingService;
import com.kynsof.payment.infrastructure.service.readExcel.ReadFileInMemoryBillingService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/billing-excel")
public class ImportExcelController {

    private final ReadFileInMemoryBillingService readFileInMemoryBillingService;
    private final ReadErrorsBillingService readErrorsBillingService;

    public ImportExcelController(ReadFileInMemoryBillingService readFileInMemoryBillingService,
            ReadErrorsBillingService readErrorsBillingService) {
        this.readFileInMemoryBillingService = readFileInMemoryBillingService;
        this.readErrorsBillingService = readErrorsBillingService;
    }

    @PostMapping(value = "/import-in-memory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<?>> importPatientInMemory(@RequestPart("file") FilePart filePart,
                                                         @RequestPart("business") String business,
                                                         @RequestPart("requestId") String requestId) {
        if (filePart == null) {
            throw new IllegalArgumentException("El FilePart no puede ser null");
        }

        return DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
                        BillingExcelErrors response = this.readFileInMemoryBillingService.readExcel(inputStream, business, requestId); // Llama al método con el InputStream
                        return Mono.just(ResponseEntity.ok(response));
                        //return Mono.just(ResponseEntity.ok(new ProcessStatus(ProcessStatusEnum.FILE_PROCESSED_CORRECTLY)));
                    } catch (IOException e) {
                        return Mono.just(ResponseEntity.ok(new ProcessStatus(ProcessStatusEnum.FILE_WITH_ERRORS, e.getMessage(), requestId)));
                    }
                });
    }

    @PostMapping(value = "/import-errors")
    public Mono<ResponseEntity<BillingExcelErrors>> importErrors(@RequestPart("requestId") String requestId) {
        BillingExcelErrors e = this.readErrorsBillingService.readErrors(requestId);
        return Mono.just(ResponseEntity.ok(e));
    }

    @ExceptionHandler(BusinessNotFoundException.class)
    public ResponseEntity<ProcessStatus> handleBusinessNotFoundError(BusinessNotFoundException e) {
        return ResponseEntity.ok(new ProcessStatus(ProcessStatusEnum.FILE_WITH_ERRORS, e.getMessage(), e.getRequestId()));
    }
}
