package com.kynsoft.notification.infrastructure.service;

import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.dto.FileInfoDto;
import com.kynsoft.notification.domain.service.IAmazonClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmazonClient implements IAmazonClient {

    private S3Client s3Client;
    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.bucketUrl}")
    private String bucketUrl;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.cloudfront.domain}")
    private String cloudfrontDomain;

    @PostConstruct
    private void initializeAmazon() {
//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
//        s3Client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
//                .region(Region.US_EAST_2).build();

        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        accessKey, secretKey)))
                .endpointOverride(URI.create(bucketUrl))
                .region(Region.of(region))  // Esta región es simbólica en este contexto
                .build();
    }

    @Override
    public void uploadFile(InputStream streamToUpload, Long size, String contentType, String objectKey)
            throws AwsServiceException, SdkClientException, IOException {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(this.bucketName).key(objectKey).contentType(contentType).contentDisposition("inline").build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(streamToUpload, streamToUpload.available()));

    }


    public void uploadFileV1(InputStream streamToUpload, Long size, String contentType, String objectKey)
            throws AwsServiceException, SdkClientException, IOException {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(this.bucketName)
                .key(objectKey).contentType(contentType).contentDisposition("inline").build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(streamToUpload, streamToUpload.available()));

    }

    @Override
    public String save(MultipartFile file, String folderPath) throws IOException {
        String originalFilename = file.getOriginalFilename();

        assert originalFilename != null;
        String sanitizedFilename = originalFilename.replace(" ", "_");

        String fileExtension = StringUtils.getFilenameExtension(sanitizedFilename);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String name = StringUtils.stripFilenameExtension(sanitizedFilename)+"." + fileExtension;

        String objectKey = folderPath+"/"+timestamp+"-"+name; // Genera la ruta completa dentro del bucket
        this.uploadFileV1(file.getInputStream(), file.getSize(), file.getContentType(), objectKey);

        return this.cloudfrontDomain +objectKey;
    }

    @Override
    public void delete(String url) {
        if (url != null && !url.isEmpty()) {
            // Extraer la clave del archivo desde la URL
            String key = url.contains(this.cloudfrontDomain) ? url.replace(this.cloudfrontDomain, "") : url;

            // Confirmar la clave antes de intentar eliminar
            System.out.println("Attempting to delete file with key: " + key);

            // Verificar la existencia del archivo antes de eliminar
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(this.bucketName)
                    .prefix(key)
                    .build();

            ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);
            if (listResponse.contents().isEmpty()) {
                System.out.println("File does not exist in the bucket: " + key);
                return; // Salir si el archivo no existe
            } else {
                System.out.println("File exists and will be deleted: " + key);
            }

            // Intentar eliminar el archivo y capturar cualquier error
            try {
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(this.bucketName)
                        .key(key)
                        .build();
                s3Client.deleteObject(deleteObjectRequest);
                System.out.println("File deleted successfully: " + key);
            } catch (AwsServiceException | SdkClientException e) {
                System.err.println("Failed to delete file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("URL is empty or null, no deletion performed.");
        }
    }

    @Override
    public AFileDto loadFile(String url) {
        try {
            // Extraer la clave del objeto a partir de la URL
            String objectKey = url.replace(this.cloudfrontDomain, "");
            
            System.out.println("DEBUG - Cargando archivo desde S3 - Bucket: " + this.bucketName + ", Key: " + objectKey);
            
            // Crear la solicitud para obtener el objeto
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(this.bucketName)
                .key(objectKey)
                .build();
            
            // Obtener el objeto de S3 directamente como un array de bytes
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            
            // Obtener los metadatos y el contenido
            GetObjectResponse objectResponse = objectBytes.response();
            String contentType = objectResponse.contentType();
            byte[] content = objectBytes.asByteArray();
            
            System.out.println("DEBUG - Contenido recibido de S3: " + content.length + " bytes");
            System.out.println("DEBUG - Tipo de contenido: " + contentType);
            
            // Crear el DTO con la información necesaria
            AFileDto fileDto = new AFileDto();
            fileDto.setUrl(url);
            
            // Establecer el nombre del archivo extrayéndolo de la clave
            String fileName = objectKey;
            int lastSlashIndex = objectKey.lastIndexOf('/');
            if (lastSlashIndex >= 0) {
                fileName = objectKey.substring(lastSlashIndex + 1);
            }
            fileDto.setName(fileName);
            System.out.println("DEBUG - Nombre de archivo detectado: " + fileName);
            
            // Establecer el tipo MIME
            if (contentType == null || contentType.isEmpty()) {
                // Intentar inferir el tipo MIME a partir de la extensión
                contentType = inferContentType(fileName);
                System.out.println("DEBUG - Tipo MIME inferido: " + contentType);
            }
            fileDto.setMimeType(contentType);
            
            // Establecer el tamaño
            fileDto.setSize((long) content.length);
            
            // NO codificar los bytes en Base64, devolverlos tal cual
            // Esto evita problemas de doble codificación
            fileDto.setFileContent(java.util.Base64.getEncoder().encodeToString(content));
            
            // Verificar que el contenido no esté vacío
            if (content.length > 0) {
                System.out.println("DEBUG - Primeros bytes (HEX): " + bytesToHex(content, 16));
            }
            
            System.out.println("DEBUG - Archivo cargado exitosamente - Nombre: " + fileName + 
                              ", Tamaño: " + content.length + " bytes, Tipo: " + contentType);
            
            return fileDto;
        } catch (NoSuchKeyException e) {
            System.err.println("ERROR - El archivo no existe en S3: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("ERROR - Error al cargar el archivo desde S3: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Convierte bytes a representación hexadecimal (para depuración)
     */
    private String bytesToHex(byte[] bytes, int limit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(bytes.length, limit); i++) {
            sb.append(String.format("%02X ", bytes[i] & 0xFF));
        }
        if (bytes.length > limit) {
            sb.append("...");
        }
        return sb.toString();
    }
    
    /**
     * Infiere el tipo de contenido basado en la extensión del archivo
     */
    private String inferContentType(String fileName) {
        if (fileName == null) {
            return "application/octet-stream";
        }
        
        String lowerCaseName = fileName.toLowerCase();
        if (lowerCaseName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerCaseName.endsWith(".png")) {
            return "image/png";
        } else if (lowerCaseName.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerCaseName.endsWith(".doc")) {
            return "application/msword";
        } else if (lowerCaseName.endsWith(".docx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (lowerCaseName.endsWith(".xls")) {
            return "application/vnd.ms-excel";
        } else if (lowerCaseName.endsWith(".xlsx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else {
            return "application/octet-stream";
        }
    }

    public List<FileInfoDto> listAllFiles(String bucketName) {
        ListObjectsV2Request req = ListObjectsV2Request.builder().bucket(bucketName).build();
        ListObjectsV2Response result = s3Client.listObjectsV2(req);

        return result.contents().stream()
                .map(s3Object -> new FileInfoDto(s3Object.key(), this.cloudfrontDomain))
                .collect(Collectors.toList());
    }



    public List<String> listAllBuckets() {
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
        List<Bucket> buckets = listBucketsResponse.buckets();

        return buckets.stream()
                .map(Bucket::name)
                .collect(Collectors.toList());
    }
}
