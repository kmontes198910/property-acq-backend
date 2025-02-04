package com.kynsoft.notification.controller;

import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.notification.application.command.file.deleteFileS3.DeleteFileS3Command;
import com.kynsoft.notification.application.command.file.deleteFileS3.DeleteFileS3Message;
import com.kynsoft.notification.application.command.file.saveFileS3.SaveFileS3Command;
import com.kynsoft.notification.application.command.file.saveFileS3.SaveFileS3Message;
import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.dto.FileInfoDto;
import com.kynsoft.notification.domain.service.IAFileService;
import com.kynsoft.notification.infrastructure.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final IMediator mediator;

    private final AmazonClient amazonClient;

    private final IAFileService fileService;

    @Autowired
    public FileController(IMediator mediator, AmazonClient amazonClient, IAFileService fileService) {
        this.mediator = mediator;
        this.amazonClient = amazonClient;
        this.fileService = fileService;
    }

    @PostMapping(value = "")
    public Mono<ResponseEntity<ApiResponse<?>>> upload(
            @RequestPart("file") FilePart filePart,
            @RequestPart("objectId") String objectId) {
        // Asignar cadena vacía si objectId es null
       String valueId = (objectId == null) ? "" : objectId;
        return DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    // Obtener el tipo de contenido (MIME type)
                    String contentType = Objects.requireNonNull(filePart.headers().getContentType()).toString();

                    // Crear MultipartFile a partir de bytes y tipo MIME
                    MultipartFile multipartFile = new MockMultipartFile(
                            UUID.randomUUID().toString(),
                            filePart.filename(),
                            contentType,
                            bytes
                    );

                    try {
                        // Pasar el objectId al comando junto con el archivo
                        SaveFileS3Message response = mediator.send(new SaveFileS3Command(multipartFile, filePart.filename(), valueId,""));
                        return Mono.just(ResponseEntity.ok(ApiResponse.success(response)));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<?> uploadFile(
            @RequestPart("file") Mono<FilePart> filePartMono,
            @RequestPart(value = "objectId", required = false) String objectId,
            @RequestPart(value = "folderPath") String folderPath) {

        String valueId = Optional.ofNullable(objectId).orElse(""); // Si es null, asigna ""

        return filePartMono
                .flatMap(filePart -> DataBufferUtils.join(filePart.content())
                        .flatMap(dataBuffer -> {
                            try {
                                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(bytes);
                                DataBufferUtils.release(dataBuffer); // Liberar memoria

                                // Obtener el tipo de contenido
                                String contentType = Optional.ofNullable(filePart.headers().getContentType())
                                        .map(MediaType::toString)
                                        .orElse("application/octet-stream"); // Valor por defecto

                                // Crear MultipartFile simulado
                                MultipartFile multipartFile = new MockMultipartFile(
                                        filePart.filename(), // Nombre del archivo
                                        filePart.filename(),
                                        contentType,
                                        bytes
                                );

                                SaveFileS3Message response = mediator.send(new SaveFileS3Command(multipartFile,
                                        filePart.filename(), valueId,folderPath));
                                return Mono.just(ResponseEntity.ok(ApiResponse.success(response)));

                            } catch (Exception e) {
                                return Mono.error(new RuntimeException("Error al procesar el archivo", e));
                            }
                        })
                );
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestBody String url) {
        try {
            DeleteFileS3Command command = new DeleteFileS3Command(url);
            DeleteFileS3Message message =mediator.send(command);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete file: " + e.getMessage());
        }
    }

    @GetMapping("/load")
    public ResponseEntity<AFileDto> loadFile(@RequestParam("url") String fileUrl) {
        try {
            AFileDto file = amazonClient.loadFile(fileUrl);
            return ResponseEntity.ok(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/load/by/id")
    public ResponseEntity<AFileDto> loadFile(@RequestParam("id") UUID id) {

        try {
            AFileDto fileSave = this.fileService.findById(id);

            AFileDto file = amazonClient.loadFile(fileSave.getUrl());
            return ResponseEntity.ok(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public List<FileInfoDto> listFiles(@RequestParam("bucketName") String bucketName) {
        return amazonClient.listAllFiles(bucketName);
    }


    @GetMapping("/buckets")
    public List<String> listBuckets() {
        return amazonClient.listAllBuckets();
    }
}


