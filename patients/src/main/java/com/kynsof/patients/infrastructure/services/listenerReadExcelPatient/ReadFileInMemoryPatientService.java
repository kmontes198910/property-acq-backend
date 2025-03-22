package com.kynsof.patients.infrastructure.services.listenerReadExcelPatient;

import com.alibaba.excel.EasyExcel;
import com.kynsof.patients.domain.dto.excel.PatientExcel;
import com.kynsof.patients.infrastructure.repository.command.ContactInfoWriteDataJPARepository;
import com.kynsof.patients.infrastructure.repository.command.PatientsWriteDataJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
public class ReadFileInMemoryPatientService {

    private final ContactInfoWriteDataJPARepository contactInfoWriteDataJPARepository;
    private final PatientsWriteDataJPARepository patientsWriteDataJPARepository;

    public ReadFileInMemoryPatientService(ContactInfoWriteDataJPARepository contactInfoWriteDataJPARepository,
                                          PatientsWriteDataJPARepository patientsWriteDataJPARepository) {
        this.contactInfoWriteDataJPARepository = contactInfoWriteDataJPARepository;
        this.patientsWriteDataJPARepository = patientsWriteDataJPARepository;
    }

    @Transactional
    public void leerExcel(InputStream inputStream) throws IOException {
        String requestId = UUID.randomUUID().toString();
        EasyExcel.read(
                inputStream,
                PatientExcel.class,
                new PatientListenerInMemory(requestId, contactInfoWriteDataJPARepository, patientsWriteDataJPARepository))
                .sheet()
                .doRead();
    }
}
