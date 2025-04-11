package com.kynsof.share.core.domain.exception;

import com.kynsof.share.core.domain.response.ApiError;
import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.domain.response.ErrorField;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleHttpClientErrorException(HttpClientErrorException ex) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        ApiError apiError = new ApiError("HTTP Error: " + ex.getMessage(), null);
        ApiResponse<?> apiResponse = ApiResponse.fail(apiError);
        return Mono.just(ResponseEntity.status(status).body(apiResponse));
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleCustomUnauthorizedException(CustomUnauthorizedException ex) {
        ApiError apiError = new ApiError("An unexpected null value was encountered.", null);
        ApiResponse<?> apiResponse = ApiResponse.fail(apiError);
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse));
    }

    @ExceptionHandler(AuthenticateNotFoundException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleAuthenticateNotFoundException(AuthenticateNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),
                List.of(ex.getErrorField()));
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.fail(apiError)));
    }

    @ExceptionHandler(UserChangePasswordException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleUserChangePasswordException(UserChangePasswordException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),
                List.of(ex.getErrorField()));
        return Mono.just(ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(ApiResponse.fail(apiError)));
    }

    @ExceptionHandler(BusinessNotFoundException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleBusinessException(BusinessNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(),
                List.of(ex.getBrokenRule().getErrorField()));
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(apiError)));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleUserNotFoundException(UserNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),
                List.of(ex.getErrorField()));
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(apiError)));
    }

    @ExceptionHandler(javax.ws.rs.NotFoundException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleNotFoundException(javax.ws.rs.NotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), null);
        ApiResponse<?> apiResponse = ApiResponse.fail(apiError);
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse));
    }

    @ExceptionHandler(NullPointerException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleNullPointerException(NullPointerException ex) {
        ApiError apiError = new ApiError("An unexpected null value was encountered.", null);
        ApiResponse<?> apiResponse = ApiResponse.fail(apiError);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse));
    }

    @ExceptionHandler(UserEmailDifferentException.class)
    public Mono<ResponseEntity<ApiResponse<?>>> handleUserEmailDifferentException(UserEmailDifferentException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),
                List.of(ex.getErrorField()));
        return Mono.just(ResponseEntity.status(555).body(ApiResponse.fail(apiError)));
    }
}
