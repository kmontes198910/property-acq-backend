package com.kynsof.patients.infrastructure.services.listenerReadExcelPatient;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.domain.dto.excel.PatientExcel;
import com.kynsof.patients.infrastructure.entity.ContactInformation;
import com.kynsof.patients.infrastructure.entity.Patients;
import com.kynsof.patients.infrastructure.repository.command.ContactInfoWriteDataJPARepository;
import com.kynsof.patients.infrastructure.repository.command.PatientsWriteDataJPARepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PatientListenerInMemory extends AnalysisEventListener<PatientExcel> {

    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Pool de hilos
    private static final int BATCH_SIZE = 10;
    private List<PatientExcel> filasAcumuladas = new ArrayList<>();

    private final String uuid;
    private final ContactInfoWriteDataJPARepository contactInfoWriteDataJPARepository;
    private final PatientsWriteDataJPARepository patientsWriteDataJPARepository;
    private final CountDownLatch latch;

    private final List<Patients> patients;
    private final List<ContactInformation> contactInformations;

    public PatientListenerInMemory(String uuid, 
                                   ContactInfoWriteDataJPARepository contactInfoWriteDataJPARepository,
                                   PatientsWriteDataJPARepository patientsWriteDataJPARepository) {
        this.uuid = uuid;
        this.latch = new CountDownLatch(1); // Inicializar el CountDownLatch
        this.patients = new ArrayList<>();
        this.contactInformations = new ArrayList<>();
        this.contactInfoWriteDataJPARepository = contactInfoWriteDataJPARepository;
        this.patientsWriteDataJPARepository = patientsWriteDataJPARepository;
    }

    @Override
    public void invoke(PatientExcel t, AnalysisContext context) {
        t.setRowIndex(context.readRowHolder().getRowIndex()); // Definiendo el numero de la fila
        filasAcumuladas.add(t);
        if (filasAcumuladas.size() >= BATCH_SIZE) {
            List<PatientExcel> batch = new ArrayList<>(filasAcumuladas);
            executor.submit(() -> {
                try {
                    writeToRedis(batch);
                } finally {
                    latch.countDown(); // Indicar que la tarea ha terminado
                }
            });
            filasAcumuladas = new ArrayList<>(); // Limpia la lista para el siguiente bloque
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext ac) {
        if (!filasAcumuladas.isEmpty()) {
            executor.submit(() -> {
                try {
                    writeToRedis(filasAcumuladas);
                } finally {
                    latch.countDown(); // Indicar que la tarea ha terminado
                }
            });
        }
        executor.shutdown(); // No aceptar más tareas

        try {
            // Esperar a que todas las tareas terminen.
            if (!executor.awaitTermination(20, TimeUnit.SECONDS)) {
                System.err.println("Las tareas no terminaron en el tiempo esperado.");
                executor.shutdownNow(); // Cancelar tareas pendientes
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El procesamiento fue interrumpido.");
            executor.shutdownNow(); // Cancelar tareas pendientes
        }
        this.patientsWriteDataJPARepository.saveAll(patients);
        this.contactInfoWriteDataJPARepository.saveAll(contactInformations);
    }

    private void writeToRedis(List<PatientExcel> dataList) {
        for (PatientExcel data : dataList) {
            Patients patient = mapToPatientExcel(data);
            this.patients.add(patient);
            this.contactInformations.add(this.mapToContactInformationExcel(data, patient));
            
        }
    }

    private Patients mapToPatientExcel(PatientExcel data) {
        Patients p = new Patients();
        p.setId(UUID.randomUUID());
        p.setIdentification(data.getIdentification());
        p.setFirstName(data.getName());
        p.setLastName(data.getLastName());
        p.setGender(GenderType.valueOf(data.getGender()));
        p.setSkinColor(data.getSkinColor());
        return p;
    }

    private ContactInformation mapToContactInformationExcel(PatientExcel data, Patients patients) {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setId(UUID.randomUUID());
        contactInformation.setEmail(data.getEmail());
        contactInformation.setBirthdayDate(LocalDate.parse(data.getBirthdayDate()));
        contactInformation.setTelephone(data.getTelephone());
        contactInformation.setPatient(patients);
        return contactInformation;
    }

}
