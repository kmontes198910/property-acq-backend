package com.kynsof.patients.infrastructure.services.listenerReadExcelPatient;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.kynsof.patients.domain.dto.excel.PatientExcel;
import com.kynsof.patients.domain.service.IPatientsService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PatientListenerWriteToRedis extends AnalysisEventListener<PatientExcel> {

    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Pool de hilos
    private static final int BATCH_SIZE = 10;
    private List<PatientExcel> filasAcumuladas = new ArrayList<>();
    private List<PatientExcel> acumuladas = new ArrayList<>();

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;

    private final String uuid;
    private final Set<String> clientIds;
    private final IPatientsService patientService;
    private static final JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), REDIS_HOST, REDIS_PORT);
    private final CountDownLatch latch;

    public PatientListenerWriteToRedis(String uuid,
            IPatientsService patientService) {
        this.uuid = uuid;
        this.clientIds = new HashSet<>();
        this.patientService = patientService;
        this.latch = new CountDownLatch(1); // Inicializar el CountDownLatch
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
//        List<ManageClient> clients = this.clientService.findByManageClientIds(clientIds);
    }

    private void writeErrorToRedis(Pipeline pipeline, String rowIndex, String value, String fieldError, String msg) {
        String keyError = "error:" + uuid + ":" + UUID.randomUUID().toString();
        pipeline.hset(keyError, "rowIndex", rowIndex);
        pipeline.hset(keyError, "valueError", value);
        pipeline.hset(keyError, "fieldError", fieldError);
        pipeline.hset(keyError, "msg", msg);
    }

    private void writeToRedis(List<PatientExcel> dataList) {
        try (Jedis jedis = jedisPool.getResource()) {
            Pipeline pipeline = jedis.pipelined();
            for (PatientExcel data : dataList) {

                // Escribir cada registro en Redis (usando un hash, por ejemplo)
                String key = "data:" + uuid + ":" + UUID.randomUUID().toString();
                pipeline.hset(key, "identification", data.getIdentification());
                pipeline.hset(key, "name", data.getName());
                pipeline.hset(key, "lastName", data.getLastName());
                pipeline.hset(key, "gender", data.getGender());
                pipeline.hset(key, "email", data.getEmail());
                pipeline.hset(key, "telephone", data.getTelephone());
                pipeline.hset(key, "birthdayDate", data.getBirthdayDate());
                pipeline.hset(key, "rowIndex", String.valueOf(data.getRowIndex()));
            }
            pipeline.sync(); // Enviar todos los comandos al servidor Redis
        }
    }

}
