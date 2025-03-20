//package com.kynsof.patients.domain.service;
//
//import com.kynsof.share.core.domain.kafka.entity.FileKafka;
//import com.kynsof.share.core.domain.kafka.producer.s3.ProducerSaveFileEventService;
//
//import java.util.UUID;
//
//
//public class SendFileService  {
//
//    private final ProducerSaveFileEventService saveFileEventService;
//
//    public SendFileService(ProducerSaveFileEventService saveFileEventService) {
//        this.saveFileEventService = saveFileEventService;
//    }
//
//    public String sendImage(String name, byte[] file) {
//        UUID photoId = UUID.randomUUID();
//        FileKafka fileSave = new FileKafka(photoId, "patients", name + ".png", file);
//        saveFileEventService.create(fileSave);
//        return photoId.toString();
//    }
//}
