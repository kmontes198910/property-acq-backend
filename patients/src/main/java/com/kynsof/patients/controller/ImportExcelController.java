package com.kynsof.patients.controller;

import com.kynsof.patients.infrastructure.services.listenerReadExcelPatient.ReadFileInMemoryPatientService;
import com.kynsof.patients.infrastructure.services.listenerReadExcelPatient.ReadFilePatientService;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/patient-excel")
public class ImportExcelController {

    private final ReadFilePatientService readFilePatientService;
    private final ReadFileInMemoryPatientService readFileInMemoryPatientService;

    public ImportExcelController(ReadFilePatientService readFilePatientService,
            ReadFileInMemoryPatientService readFileInMemoryPatientService) {
        this.readFilePatientService = readFilePatientService;
        this.readFileInMemoryPatientService = readFileInMemoryPatientService;
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<?>> importPatientRedis(@RequestPart("file") FilePart filePart) {
        if (filePart == null) {
            throw new IllegalArgumentException("El FilePart no puede ser null");
        }

        return DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
                        this.readFilePatientService.leerExcel(inputStream); // Llama al método con el InputStream
                        return Mono.just(ResponseEntity.ok("Archivo procesado correctamente."));
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                });
    }

    @PostMapping(value = "/import-in-memory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<?>> importPatientInMemory(@RequestPart("file") FilePart filePart) {
        if (filePart == null) {
            throw new IllegalArgumentException("El FilePart no puede ser null");
        }

        return DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
                        this.readFileInMemoryPatientService.leerExcel(inputStream); // Llama al método con el InputStream
                        return Mono.just(ResponseEntity.ok("Archivo procesado correctamente."));
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                });
    }

}
