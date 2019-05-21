package com.webshop.payment.error.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"object", "field", "message"})
public class ApiSubError {

    @ApiModelProperty(value = "Object", required = true)
    private String object;
    @ApiModelProperty(value = "Invalid field", required = true)
    private String field;
    @ApiModelProperty(value = "Error message", required = true)
    private String message;

}

