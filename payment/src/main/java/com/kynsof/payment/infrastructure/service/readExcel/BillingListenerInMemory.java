package com.kynsof.payment.infrastructure.service.readExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
import com.kynsof.payment.domain.dto.excel.BillingExcel;
import com.kynsof.payment.domain.dto.excel.RowProcess;
import com.kynsof.payment.infrastructure.entity.Billing;
import com.kynsof.payment.infrastructure.entity.Business;
import com.kynsof.payment.infrastructure.entity.Client;
import com.kynsof.payment.infrastructure.repositories.command.BillingWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.ClientReadDataJPARepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BillingListenerInMemory extends AnalysisEventListener<BillingExcel> {

    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Pool de hilos
    private static final int BATCH_SIZE = 10;
    private List<BillingExcel> filasAcumuladas = new ArrayList<>();

    private final String uuid;
    private final String key;
    private final Business business;
    private final ClientReadDataJPARepository clientReadDataJPARepository;
    private final BillingWriteDataJPARepository billingWriteDataJPARepository;
    private final CountDownLatch latch;

    private final List<Billing> billings;
    private final RowProcess rowProcess;
    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final SetOperations<String, String> setOperations;

    public BillingListenerInMemory(String uuid,
            String key,
            Business business,
            ClientReadDataJPARepository clientReadDataJPARepository,
            BillingWriteDataJPARepository billingWriteDataJPARepository,
            RedisTemplate<String, String> redisTemplate,
            HashOperations<String, String, String> hashOperations,
            SetOperations<String, String> setOperations,
            RowProcess rowProcess) {
        this.uuid = uuid;
        this.key = key;
        this.business = business;
        this.latch = new CountDownLatch(1); // Inicializar el CountDownLatch
        this.billings = new ArrayList<>();
        this.clientReadDataJPARepository = clientReadDataJPARepository;
        this.billingWriteDataJPARepository = billingWriteDataJPARepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = hashOperations;
        this.setOperations = setOperations;
        this.rowProcess = rowProcess;
    }

    @Override
    public void invoke(BillingExcel t, AnalysisContext context) {
        t.setRowIndex(context.readRowHolder().getRowIndex()); // Definiendo el numero de la fila
        filasAcumuladas.add(t);
        if (filasAcumuladas.size() >= BATCH_SIZE) {
            List<BillingExcel> batch = new ArrayList<>(filasAcumuladas);
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
        this.billingWriteDataJPARepository.saveAll(billings);
    }

    private void writeToRedis(List<BillingExcel> dataList) {
        for (BillingExcel data : dataList) {
            Optional<Client> client = this.clientReadDataJPARepository.findByIdentification(data.getIdentification());
            if (!client.isPresent()) {
                String uuidError = UUID.randomUUID().toString();
                String errorKey = "error:" + uuidError;
                hashOperations.put(errorKey, "rowIndex", String.valueOf(data.getRowIndex()));
                hashOperations.put(errorKey, "column", "Identification");
                hashOperations.put(errorKey, "value", data.getIdentification());
                hashOperations.put(errorKey, "msg", "Client not found!");

                setOperations.add(key, uuidError);
                rowProcess.setCostNotProcess(rowProcess.getCostNotProcess() + data.getCost());
                rowProcess.setRowNotProcess(rowProcess.getRowNotProcess() + 1);
                continue;//Aqui se debe de llevar para redis una lista de errores.
            }
            rowProcess.setCostProcess(rowProcess.getCostProcess() + data.getCost());
            rowProcess.setRowProcess(rowProcess.getRowProcess() + 1);
            Billing billing = mapToPatientExcel(data, client.get());
            this.billings.add(billing);
        }
    }

    private Billing mapToPatientExcel(BillingExcel data, Client client) {
        Billing billing = new Billing();
        billing.setBusiness(business);
        billing.setId(UUID.randomUUID());
        billing.setCode(data.getCode());
        billing.setCost(data.getCost());
        billing.setDescription(data.getDescription());
        billing.setTypeOperation(TypeOperation.Other);
        billing.setStatus(BillingStatus.PENDING_PAID);
        billing.setClient(client);
        return billing;
    }

}
