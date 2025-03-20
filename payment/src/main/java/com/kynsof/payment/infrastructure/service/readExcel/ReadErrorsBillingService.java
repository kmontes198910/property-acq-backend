package com.kynsof.payment.infrastructure.service.readExcel;

import com.kynsof.payment.domain.dto.excel.BillingExcelErrors;
import com.kynsof.payment.domain.dto.excel.Errors;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReadErrorsBillingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final SetOperations<String, String> setOperations;

    public ReadErrorsBillingService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.setOperations = redisTemplate.opsForSet();
    }

    public BillingExcelErrors readErrors(String requestId) {
        BillingExcelErrors response = new BillingExcelErrors();
        response.setRequestId(requestId);
        List<Errors> errors = new ArrayList<>();
        String key = "read:" + requestId;

        // Obtener todos los UUIDs de errores asociados a esta lectura
        Set<String> errores = setOperations.members(key);
        Set<String> deleteErrors = new HashSet<>();

        if (errores != null) {
            for (String uuidError : errores) {
                Errors e = new Errors();
                String errorKey = "error:" + uuidError;
                Map<String, String> detallesError = hashOperations.entries(errorKey);

                e.setRowIndex(detallesError.get("rowIndex"));
                e.setColumn(detallesError.get("column"));
                e.setValue(detallesError.get("value"));
                e.setMsg(detallesError.get("msg"));
                errors.add(e);
                deleteErrors.add(errorKey);
            }
            redisTemplate.delete(key);
            redisTemplate.delete(deleteErrors);
        } else {
            System.out.println("No se encontraron errores para la lectura: " + requestId);
        }

        response.setError(errors);
        return response;
    }
}
