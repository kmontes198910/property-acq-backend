package com.kynsof.treatments.application.service.listenerReadExcel;

import com.alibaba.excel.EasyExcel;
import com.kynsof.treatments.domain.dto.excel.MedicinesExcel;
import com.kynsof.treatments.infrastructure.repositories.command.MedicinesWriteDataJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional
public class ReadFileInMemoryService {

    private final MedicinesWriteDataJPARepository repositoryCommand;

    public ReadFileInMemoryService(MedicinesWriteDataJPARepository repositoryCommand) {
        this.repositoryCommand = repositoryCommand;
    }

    @Transactional
    public void leerExcel(InputStream inputStream) throws IOException {
        EasyExcel.read(
                inputStream,
                MedicinesExcel.class,
                new ListenerInMemory(repositoryCommand))
                .sheet()
                .doRead();
    }
}
