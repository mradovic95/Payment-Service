package com.webshop.payment.error.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.webshop.payment.exception.CustomException;
import com.webshop.payment.exception.SubError;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonPropertyOrder({"error", "error_description", "error_code", "timestamp", "sub_errors"})
public class ApiError {

    @ApiModelProperty(value = "Error code", required = true)
    @JsonProperty("error")
    private String error;

    @ApiModelProperty(value = "Http status", required = true)
    @JsonProperty("error_code")
    private HttpStatus errorCode;

    @ApiModelProperty(value = "Error description", required = true)
    @JsonProperty("error_description")
    private String errorDescription;

    @ApiModelProperty(value = "Timestamp", required = true)
    @JsonProperty("timestamp")
    private Instant timestamp;

    @ApiModelProperty(value = "List of sub errors", required = true)
    @JsonProperty("sub_errors")
    private List<ApiSubError> subErrors;


    private ApiError() {
        timestamp = Instant.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.errorCode = status;
        this.error = status.name().toLowerCase();
    }

    public ApiError(HttpStatus status, String errorDescription) {
        this(status);
        this.errorDescription = errorDescription;
    }

    public ApiError(CustomException ex) {
        this(ex.getHttpStatus(), ex.getMessage());
        addSubErrors(ex.getSubErrors());
    }

    private void addSubErrors(List<SubError> subErrors) {
        subErrors.forEach(subError -> addSubError(new ApiSubError(subError.getObject(), subError.getField(), subError.getMessage())));
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addSubError(String object, String field, String message) {
        addSubError(new ApiSubError(object, field, message));
    }

    private void addSubError(ObjectError objectError) {
        this.addSubError(
                objectError.getObjectName(),
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    private void addSubError(FieldError fieldError) {
        this.addSubError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addSubError);
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addSubError);
    }

}
