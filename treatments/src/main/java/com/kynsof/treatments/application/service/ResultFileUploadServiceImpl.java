package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.treatments.application.command.result.create.FileBase64UploadDto;
import com.kynsof.treatments.domain.service.IResultFileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Implementación del servicio para cargar archivos de resultados
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResultFileUploadServiceImpl implements IResultFileUploadService {

    private final RestTemplate restTemplate;
    
    @Value("${services.file-service.url:http://localhost:8097}")
    private String fileServiceUrl;
    
    @Override
    public String uploadFile(String base64Content, String fileName, String objectId, 
                           String folderPath, String uploadedById, String uploadedByUsername) {
        try {
            // Crear el DTO para la carga del archivo
            FileBase64UploadDto fileUploadDto = new FileBase64UploadDto();
            fileUploadDto.setBase64Content(base64Content);
            fileUploadDto.setFileName(fileName);
            fileUploadDto.setObjectId(objectId);
            fileUploadDto.setFolderPath(folderPath);
            
            // Configurar los headers para la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (uploadedById != null) {
                headers.set("X-User-Id", uploadedById);
            }
            if (uploadedByUsername != null) {
                headers.set("X-User-Name", uploadedByUsername);
            }
            
            // Crear la entidad de la solicitud
            HttpEntity<FileBase64UploadDto> requestEntity = new HttpEntity<>(fileUploadDto, headers);
            
            // Realizar la llamada al servicio
            String endpoint = fileServiceUrl + "/api/file-record";
            ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                    endpoint,
                    requestEntity,
                    ApiResponse.class
            );
            
            // Procesar la respuesta
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getData() != null) {
                // Extraer la URL del archivo cargado desde la respuesta
                Object data = response.getBody().getData();
                if (data instanceof java.util.Map) {
                    @SuppressWarnings("unchecked")
                    java.util.Map<String, Object> dataMap = (java.util.Map<String, Object>) data;
                    return (String) dataMap.get("viewUrl");
                }
            }
            
            throw new RuntimeException("No se pudo obtener la URL del archivo cargado");
            
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el archivo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean deleteFile(String fileId) {
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
            return response.getStatusCode().is2xxSuccessful();
            
        } catch (Exception e) {
            log.error("Error al eliminar el archivo con ID {}: {}", fileId, e.getMessage(), e);
            return false;
        }
    }
}