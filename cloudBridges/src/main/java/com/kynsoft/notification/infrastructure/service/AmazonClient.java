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
        String filename = url.replace(this.cloudfrontDomain, "");

        return new AFileDto(filename, url);
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
