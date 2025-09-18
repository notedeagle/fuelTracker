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

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public void addValidationErrors(Map<String, String> fieldErrors) {
        if (validationErrors == null) {
            validationErrors = new ArrayList<>();
        }

        fieldErrors.forEach((field, errorMessage) ->
                validationErrors.add(new ApiValidationError("DTO", field, null, errorMessage))
        );
    }
}
