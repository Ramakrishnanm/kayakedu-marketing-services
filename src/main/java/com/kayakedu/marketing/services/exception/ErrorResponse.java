package com.kayakedu.marketing.services.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.kayakedu.marketing.services.payload.model.FieldErrorModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@lombok.Getter
@lombok.Setter
@ApiModel(description = "Model representing the error response.")
public class ErrorResponse {

	@ApiModelProperty(notes = "Identifier for the base error cause.", required = true)
    private ErrorScenario errorCode;

    @ApiModelProperty(notes = "Describes the error in greater details."
    								+ "It also holds the tracking information.")
    private String errorDesc;

    @ApiModelProperty(notes = "Usage: Form validations at server."
    					+ "Suggests why server rejected a particular form path /attribute.")
    private List<FieldErrorModel> errorCauses;

	@ApiModelProperty(notes = "Timestamp when the particular error occurred.")
	private LocalDateTime timestamp = LocalDateTime.now();
	
}
