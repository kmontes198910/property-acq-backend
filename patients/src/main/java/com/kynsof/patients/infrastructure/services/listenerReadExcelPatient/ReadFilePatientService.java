package com.kynsof.patients.infrastructure.services.listenerReadExcelPatient;

import com.alibaba.excel.EasyExcel;
import com.kynsof.patients.domain.dto.excel.PatientExcel;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.patients.infrastructure.entity.ContactInformation;
import com.kynsof.patients.infrastructure.entity.Patients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReadFilePatientService {

    private final IPatientsService patientService;
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), REDIS_HOST, REDIS_PORT);
    private final ReadToRedis leerDeRedis;

    public ReadFilePatientService(IPatientsService patientService, ReadToRedis leerDeRedis) {
        this.patientService = patientService;
        this.leerDeRedis = leerDeRedis;
    }

    @Transactional
    public void leerExcel(InputStream inputStream) throws IOException {
        String requestId = UUID.randomUUID().toString();
        List<Patients> patients = new ArrayList<>();
        List<ContactInformation> contactInformations = new ArrayList<>();
        EasyExcel.read(
                inputStream,
                PatientExcel.class,
                new PatientListenerWriteToRedis(requestId, patientService))
                .sheet()
                .doRead();
        this.leerDeRedis.readFromRedisByUuid(requestId, patients, contactInformations);
        System.err.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.err.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.err.println("Patients: " + patients.size());
        System.err.println("Contactos: " + contactInformations.size());
        System.err.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.err.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
    }
//
//    @Async
//    private void insert(List<ManageTrabajador> trabajadores) {
//        this.manageTrabajadorService.createAll(trabajadores);
//    }
}
