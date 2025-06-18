package com.kynsof.treatments.application.service.listenerReadExcel;

import com.alibaba.excel.EasyExcel;
import com.kynsof.treatments.domain.dto.excel.MedicinesExcel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional
public class ReadFileInMemoryService {

    public ReadFileInMemoryService() {
    }

    @Transactional
    public void leerExcel(InputStream inputStream) throws IOException {
        EasyExcel.read(
                inputStream,
                MedicinesExcel.class,
                new ListenerInMemory())
                .sheet()
                .doRead();
    }
}
