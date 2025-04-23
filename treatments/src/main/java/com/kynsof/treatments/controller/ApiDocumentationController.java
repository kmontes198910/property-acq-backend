package com.kynsof.treatments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api-docs")
public class ApiDocumentationController {

    private final ApplicationContext applicationContext;

    @Autowired
    public ApiDocumentationController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getApiDocumentation() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        Map<String, List<Map<String, String>>> endpoints = new TreeMap<>();

        handlerMethods.forEach((mappingInfo, handlerMethod) -> {
            // Skip this controller to avoid infinite recursion
            if (handlerMethod.getBeanType().equals(ApiDocumentationController.class)) {
                return;
            }

            // Get controller name
            String controllerName = handlerMethod.getBeanType().getSimpleName();

            // Get HTTP methods and paths
            Set<String> httpMethods = mappingInfo.getMethodsCondition().getMethods().stream()
                    .map(method -> method.name())
                    .collect(Collectors.toSet());

            Set<String> paths = mappingInfo.getPatternsCondition().getPatterns();

            // Get method name and description
            String methodName = handlerMethod.getMethod().getName();

            // Create endpoint info
            for (String path : paths) {
                for (String httpMethod : httpMethods) {
                    Map<String, String> endpointInfo = new HashMap<>();
                    endpointInfo.put("method", httpMethod);
                    endpointInfo.put("path", path);
                    endpointInfo.put("methodName", methodName);

                    // Add endpoint to the list
                    endpoints.computeIfAbsent(controllerName, k -> new ArrayList<>())
                            .add(endpointInfo);
                }
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("apiName", "Treatments API");
        response.put("version", "1.0.0");
        response.put("endpoints", endpoints);

        return ResponseEntity.ok(response);
    }
}