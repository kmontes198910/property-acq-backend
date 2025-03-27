package com.kynsof.calendar.application.service;

import com.kynsof.calendar.application.command.receipt.updateStatus.DiscountRequest;
import com.kynsof.calendar.domain.service.IBusinessBalanceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class BusinessBalanceService implements IBusinessBalanceService {

    private final RestTemplate restTemplate;

    @Value("${business.balance.discount.url:http://localhost:9905/api/business-balance/discount}")
    private String discountUrl;

    public BusinessBalanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String discountBusinessBalance(UUID businessId, double balance) {
        DiscountRequest request = new DiscountRequest();
        request.setBusinessId(businessId);
        request.setBalance(balance);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<DiscountRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                discountUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }
}