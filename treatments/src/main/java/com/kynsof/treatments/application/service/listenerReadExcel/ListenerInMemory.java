package com.kynsof.treatments.application.service.listenerReadExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.kynsof.treatments.domain.dto.excel.MedicinesExcel;
import com.kynsof.treatments.infrastructure.entity.Medicines;
import com.kynsof.treatments.infrastructure.repositories.command.MedicinesWriteDataJPARepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ListenerInMemory extends AnalysisEventListener<MedicinesExcel> {

    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Pool de hilos
    private static final int BATCH_SIZE = 10000;
    private List<MedicinesExcel> filasAcumuladas = new ArrayList<>();

    private final CountDownLatch latch;

    private final MedicinesWriteDataJPARepository repositoryCommand;

    public ListenerInMemory(MedicinesWriteDataJPARepository repositoryCommand) {
        this.latch = new CountDownLatch(1); // Inicializar el CountDownLatch
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public void invoke(MedicinesExcel t, AnalysisContext context) {
        t.setRowIndex(context.readRowHolder().getRowIndex()); // Definiendo el numero de la fila
        filasAcumuladas.add(t);
        if (filasAcumuladas.size() >= BATCH_SIZE) {
            List<MedicinesExcel> batch = new ArrayList<>(filasAcumuladas);
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
    }

    private void writeToRedis(List<MedicinesExcel> filasAcumuladas) {
        List<Medicines> medicineses = new ArrayList<>();
        for (MedicinesExcel row : filasAcumuladas) {
            System.err.println("|||||||||||||||||||||||||||||||||||||||||||||");
            System.err.println("|||||||||||||||||||||||||||||||||||||||||||||");
            System.err.println("Producto: " + row.getProduct());
            System.err.println("line: " + row.getRowIndex());
            System.err.println("|||||||||||||||||||||||||||||||||||||||||||||");
            System.err.println("|||||||||||||||||||||||||||||||||||||||||||||");
            medicineses.add(Medicines
                    .builder()
                    .id(UUID.randomUUID())
                    .name(row.getProduct())
                    .presentation(row.getPresentation())
                    .build());
        }
        this.repositoryCommand.saveAll(medicineses);
    }
}
