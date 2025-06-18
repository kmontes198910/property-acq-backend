package com.kynsof.treatments.controller;

import com.kynsof.treatments.application.service.listenerReadExcel.ReadFileInMemoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
public class ImportExcelController {

    private final ReadFileInMemoryService readFileInMemoryService;

    public ImportExcelController(ReadFileInMemoryService readFileInMemoryService) {
        this.readFileInMemoryService = readFileInMemoryService;
    }

    @PostMapping(value = "/import-in-memory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> importPatientInMemory(@RequestParam("file") MultipartFile file) {

        if (file == null) {
            throw new IllegalArgumentException("El archivo no puede ser null");
        }

        try (InputStream inputStream = file.getInputStream()) {
            readFileInMemoryService.leerExcel(inputStream);
            return ResponseEntity.ok("Archivo procesado correctamente");
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo Excel", e);
        }
    }
}
