package com.kayakedu.marketing.services.exception;

import org.springframework.http.HttpStatus;

@lombok.AllArgsConstructor
@lombok.Getter
public enum ErrorScenario {

	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR),
	UNEXPECTED_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
	BAD_REQUEST(HttpStatus.BAD_REQUEST),
    FORBIDDEN(HttpStatus.FORBIDDEN),

	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),
	INVALID_DATA(HttpStatus.BAD_REQUEST),
	VALIDATION_FAILURE(HttpStatus.BAD_REQUEST),
	DATA_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
	PERMISSION_NOT_ALLOWED(HttpStatus.UNAUTHORIZED),
	USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED);

	private HttpStatus httpStatus;
}
