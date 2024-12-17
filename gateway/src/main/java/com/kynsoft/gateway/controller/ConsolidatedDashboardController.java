package com.kynsoft.gateway.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class ConsolidatedDashboardController {

    private final WebClient.Builder webClientBuilder;

    public ConsolidatedDashboardController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

//    @GetMapping("/overview/{businessId}")
//    public Mono<Map<String, Object>> getDashboardOverview(@PathVariable UUID businessId) {
//        WebClient webClient = webClientBuilder.build();
//
//        int currentYear = java.time.LocalDate.now().getYear();
//
//        // Llamada al servicio de citas por estado
//        Mono<Map<String, Object>> appointmentsByStatus = webClient
//                .get()
//                .uri("lb://CALENDAR/api/dashboard/receipt-count-by-type/" + businessId)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
//                .onErrorResume(e -> Mono.just(Map.of("error", "Error fetching appointments: " + e.getMessage())));
//
//        // Llamada al servicio de consultas externas por mes
//        Mono<Map<String, Object>> consultationsByMonth = webClient
//                .get()
//                .uri("lb://TREATMENTS/api/dashboard/external-consultation-count-by-month?businessId=" + businessId + "&year=" + currentYear)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
//                .onErrorResume(e -> Mono.just(Map.of("error", "Error fetching consultations: " + e.getMessage())));
//
//        // Llamada al servicio de usuarios por tipo
//        Mono<Map<String, Object>> userCountByType = webClient
//                .get()
//                .uri("lb://IDENTITY/api/dashboard/user-count-by-type/" + businessId)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
//                .onErrorResume(e -> Mono.just(Map.of("error", "Error fetching user counts: " + e.getMessage())));
//
//        // Combinar las respuestas de los microservicios
//        return Mono.zip(appointmentsByStatus, consultationsByMonth, userCountByType)
//                .map(tuple -> {
//                    Map<String, Object> consolidatedResponse = new HashMap<>();
//                    consolidatedResponse.put("appointmentsByStatus", tuple.getT1());
//                    consolidatedResponse.put("consultationsByMonth", tuple.getT2());
//                    consolidatedResponse.put("userCountByType", tuple.getT3());
//                    return consolidatedResponse;
//                });
//    }

    @GetMapping("/overview/{businessId}")
    public Mono<Map<String, Object>> getDashboardOverview(@PathVariable UUID businessId) {
        WebClient webClient = webClientBuilder.build();

        int currentYear = java.time.LocalDate.now().getYear();

        // Llamada al servicio calendar-service
        Mono<Map<String, Object>> appointmentsByStatus = webClient
                .get()
                .uri("http://calendar-service:9909/api/dashboard/receipt-count-by-type/" + businessId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    System.err.println("Error fetching appointments: " + e.getMessage());
                    return Mono.just(Map.of("error", "Error fetching appointments: " + e.getMessage()));
                });

        // Llamada al servicio treatments-service
        Mono<Map<String, Object>> consultationsByMonth = webClient
                .get()
                .uri("http://treatments-service:9909/api/dashboard/external-consultation-count-by-month?businessId=" + businessId + "&year=" + currentYear)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    System.err.println("Error fetching consultations: " + e.getMessage());
                    return Mono.just(Map.of("error", "Error fetching consultations: " + e.getMessage()));
                });

        // Llamada al servicio identity-service para usuarios
        Mono<Map<String, Object>> userCountByType = webClient
                .get()
                .uri("http://identity-service:80/api/dashboard/user-count-by-type/" + businessId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    System.err.println("Error fetching user counts: " + e.getMessage());
                    return Mono.just(Map.of("error", "Error fetching user counts: " + e.getMessage()));
                });

        Mono<Map<String, Object>> patientCount = webClient
                .get()
                .uri("http://patient-service:80/api/dashboard/countPatient")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    System.err.println("Error fetching user counts: " + e.getMessage());
                    return Mono.just(Map.of("error", "Error fetching user counts: " + e.getMessage()));
                });

        // Consolidar todas las respuestas
        return Mono.zip(appointmentsByStatus, consultationsByMonth, userCountByType, patientCount)
                .map(tuple -> {
                    Map<String, Object> consolidatedResponse = new HashMap<>();
                    consolidatedResponse.put("appointmentsByStatus", tuple.getT1());
                    consolidatedResponse.put("consultationsByMonth", tuple.getT2());
                    consolidatedResponse.put("userCountByType", tuple.getT3());
                    consolidatedResponse.put("patientCount", tuple.getT4());
                    return consolidatedResponse;
                });
    }
}