package com.kynsof.payment.infrastructure.service.readExcel;

import com.alibaba.excel.EasyExcel;
import com.kynsof.payment.domain.dto.excel.BillingExcel;
import com.kynsof.payment.domain.dto.excel.BillingExcelErrors;
import com.kynsof.payment.domain.dto.excel.RowProcess;
import com.kynsof.payment.domain.dto.excel.exception.BusinessNotFoundException;
import com.kynsof.payment.domain.dto.excel.processStatus.ProcessStatusEnum;
import com.kynsof.payment.infrastructure.entity.Business;
import com.kynsof.payment.infrastructure.repositories.command.BillingWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.BusinessReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.ClientReadDataJPARepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReadFileInMemoryBillingService {

    private final ClientReadDataJPARepository clientReadDataJPARepository;
    private final BillingWriteDataJPARepository billingWriteDataJPARepository;
    private final BusinessReadDataJPARepository businessReadDataJPARepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final SetOperations<String, String> setOperations;
    private final ReadErrorsBillingService readErrorsBillingService;
    private final RowProcess rowProcess;

    public ReadFileInMemoryBillingService(ClientReadDataJPARepository clientReadDataJPARepository,
            BillingWriteDataJPARepository billingWriteDataJPARepository,
            BusinessReadDataJPARepository businessReadDataJPARepository,
            RedisTemplate<String, String> redisTemplate,
            ReadErrorsBillingService readErrorsBillingService) {
        this.clientReadDataJPARepository = clientReadDataJPARepository;
        this.billingWriteDataJPARepository = billingWriteDataJPARepository;
        this.businessReadDataJPARepository = businessReadDataJPARepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.setOperations = redisTemplate.opsForSet();
        this.readErrorsBillingService = readErrorsBillingService;
        this.rowProcess = new RowProcess();
    }

    @Transactional
    public BillingExcelErrors readExcel(InputStream inputStream, String businessId, String requestId) throws IOException {
        // Asociar el UUID del error al UUID principal
        String key = "read:" + requestId;
        Optional<Business> business = this.businessReadDataJPARepository.findById(UUID.fromString(businessId));
        if (!business.isPresent()) {
            throw new BusinessNotFoundException(ProcessStatusEnum.FILE_WITH_ERRORS, "Business not found!", requestId);
        }
        EasyExcel.read(
                inputStream,
                BillingExcel.class,
                new BillingListenerInMemory(requestId, key, business.get(), clientReadDataJPARepository, billingWriteDataJPARepository, redisTemplate, hashOperations, setOperations, rowProcess))
                .sheet()
                .doRead();

        BillingExcelErrors e = this.readErrorsBillingService.readErrors(requestId);
        e.setRowProcess(rowProcess);
        return e;
    }
}
