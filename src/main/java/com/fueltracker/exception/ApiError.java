package com.fueltracker.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;

    private String message;
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ApiValidationError> validationErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    /**
     * Dodaje błędy walidacji z mapy błędów (pole -> komunikat błędu)
     */
    public void addValidationErrors(Map<String, String> fieldErrors) {
        if (validationErrors == null) {
            validationErrors = new ArrayList<>();
        }

        fieldErrors.forEach((field, message) ->
                validationErrors.add(new ApiValidationError("DTO", field, null, message))
        );
    }

    /**
     * Dodaje pojedynczy błąd walidacji
     */
    public void addValidationError(String field, String message) {
        if (validationErrors == null) {
            validationErrors = new ArrayList<>();
        }
        validationErrors.add(new ApiValidationError("DTO", field, null, message));
    }
}
