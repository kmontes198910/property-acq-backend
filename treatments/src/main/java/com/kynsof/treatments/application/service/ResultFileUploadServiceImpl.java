package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.treatments.domain.service.IResultFileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

/**
 * Implementación del servicio para cargar archivos de resultados
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResultFileUploadServiceImpl implements IResultFileUploadService {

    private final RestTemplate restTemplate;
    
    @Value("${services.cloudBridges-service.url:http://localhost:8097}")
    private String fileServiceUrl;
    @Override
    public String uploadFile(MultipartFile file, String fileName, String objectId,
                             String folderPath, String uploadedById, String uploadedByUsername, UUID businessId) {
        try {
            log.info("Iniciando carga de archivo: {}, folderPath: {}, objectId: {}",
                    fileName, folderPath, objectId);

            // Crear los headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            if (uploadedById != null && !uploadedById.isEmpty()) {
                headers.set("X-User-Id", uploadedById);
                log.debug("Añadido X-User-Id: {}", uploadedById);
            }

            if (uploadedByUsername != null && !uploadedByUsername.isEmpty()) {
                headers.set("X-User-Name", uploadedByUsername);
                log.debug("Añadido X-User-Name: {}", uploadedByUsername);
            }

            // Convertir MultipartFile a ByteArrayResource para evitar problemas de serialización
            ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            // Construir formulario multipart
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("file", fileAsResource);

            // Construir URL base
            String endpoint = fileServiceUrl + "/api/file-record/upload";

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint);
            if (objectId != null && !objectId.isEmpty()) {
                builder.queryParam("objectId", objectId);
            }
            if (folderPath != null && !folderPath.isEmpty()) {
                builder.queryParam("folderPath", folderPath);
            }
            if (businessId != null) {
                builder.queryParam("businessId", businessId.toString());
            }

            String finalUrl = builder.toUriString();
            log.info("Enviando solicitud a: {}", finalUrl);

            // Enviar request
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

            ResponseEntity<ApiResponse> response = restTemplate.exchange(
                    finalUrl,
                    HttpMethod.POST,
                    requestEntity,
                    ApiResponse.class
            );

            log.info("Respuesta recibida con estado: {}", response.getStatusCode());

            if (response.getStatusCode().is2xxSuccessful() &&
                response.getBody() != null &&
                response.getBody().getData() != null) {

                Object data = response.getBody().getData();
                log.debug("Respuesta recibida: {}", data);

                if (data instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> dataMap = (Map<String, Object>) data;
                    String url = (String) dataMap.getOrDefault("viewUrl", dataMap.get("url"));
                    if (url != null) {
                        log.info("Archivo cargado exitosamente: {}", url);
                        return url;
                    }
                }
            }

            log.error("No se pudo obtener la URL del archivo. Estado: {}, Cuerpo: {}",
                    response.getStatusCode(), response.getBody());
            throw new RuntimeException("No se pudo obtener la URL del archivo cargado");

        } catch (Exception e) {
            log.error("Error al cargar archivo {}: {}", fileName, e.getMessage(), e);
            throw new RuntimeException("Error al cargar el archivo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void deleteFile(String fileId) {
        try {
            log.info("Eliminando archivo con ID: {}", fileId);
            
            // Configurar los headers para la solicitud
            HttpHeaders headers = new HttpHeaders();
            
            // Crear la entidad de la solicitud
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            
            // Realizar la llamada al servicio de eliminación por ID
            String endpoint = fileServiceUrl + "/api/file-record/" + fileId;
            ResponseEntity<ApiResponse> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.DELETE,
                    requestEntity,
                    ApiResponse.class
            );
            
            // Verificar si la eliminación fue exitosa
            response.getStatusCode().is2xxSuccessful();

        } catch (Exception e) {
            log.error("Error al eliminar el archivo con ID {}: {}", fileId, e.getMessage(), e);
        }
    }
}