package com.kayakedu.marketing.services.advice;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kayakedu.marketing.services.exception.ErrorResponse;
import com.kayakedu.marketing.services.exception.ErrorScenario;
import com.kayakedu.marketing.services.exception.ServiceException;
import com.kayakedu.marketing.services.payload.model.FieldErrorModel;

import lombok.extern.log4j.Log4j2;

/**
 * @author R.Manoharan
 *
 * Exception handler is responsible for catching the uncaught exceptions, logging them,
 * and returning a proper exception wrapped in defined structure to the invoker.
 * <p>
 * It's shared across all the service <tt>@Controller</tt> classes.
 *
 */
@ControllerAdvice
@Log4j2
public class GenericExceptionHandler {
	

	@ExceptionHandler(ServiceException.class)
	public final ResponseEntity<ErrorResponse> handleWorkflowServiceExceptions(
												ServiceException ex) {

		log.error("Caught Workflow-Service Exception -", ex);

		// Prepare ErrorResponse from WorkflowServiceException instance
		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(ex.getScenario());
		response.setErrorDesc(ex.getMessage());
		return serviceErrorResponse(response);
	}

	/*
	 * @ExceptionHandler(ConstraintViolationException.class) public
	 * ResponseEntity<ErrorResponse> handleConstraintViolation(
	 * ConstraintViolationException ex) {
	 * 
	 * // Alter ConstraintViolations from ConstraintViolationException instance to
	 * FieldErrorModels List<FieldErrorModel> errors = new ArrayList<>(); for
	 * (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	 * FieldErrorModel fieldError = new FieldErrorModel(
	 * violation.getRootBeanClass().getName() + " " + violation.getPropertyPath(),
	 * violation.getMessage()); errors.add(fieldError); }
	 * 
	 * // Prepare ErrorResponse from ConstraintViolationException instance
	 * ErrorResponse response = new ErrorResponse();
	 * response.setErrorCode(ErrorScenario.VALIDATION_FAILURE);
	 * response.setErrorCauses(errors); response.setErrorDesc(ex.getMessage());
	 * return new ResponseEntity<>(response, new HttpHeaders(),
	 * response.getErrorCode().getHttpStatus()); }
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidViolation(
											MethodArgumentNotValidException ex) {

		// Alter ObjectErrors from MethodArgumentNotValidException instance to FieldErrorModels
		List<FieldErrorModel> errors = new ArrayList<>();
		for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
			FieldErrorModel feidError = new FieldErrorModel(objectError.getCodes()[0]
																.replace("Pattern.", ""),
					objectError.getDefaultMessage());
			errors.add(feidError);
		}

		// Prepare ErrorResponse from MethodArgumentNotValidException instance
		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(ErrorScenario.VALIDATION_FAILURE);
		response.setErrorCauses(errors);
		response.setErrorDesc(ex.getMessage());
		return new ResponseEntity<>(response, new HttpHeaders(),
				response.getErrorCode().getHttpStatus());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ErrorResponse> handleAccessDeniedExceptions(
													AccessDeniedException ex) {

		log.error("Caught Workflow-Security Exception -", ex);

		// Prepare ErrorResponse from AccessDeniedException instance
		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(ErrorScenario.PERMISSION_NOT_ALLOWED);
		response.setErrorDesc(ex.getMessage());
		return serviceErrorResponse(response);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleUncaughtExceptions(Exception ex) {

		// Fetch the cause from Exception instance
		String cause = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();

		log.error("Something is broken. Cause: " + cause, ex);

		// Prepare ErrorResponse from Exception instance
		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(ErrorScenario.UNKNOWN);
		response.setErrorDesc(cause);
		return serviceErrorResponse(response);
	}

	private ResponseEntity<ErrorResponse> serviceErrorResponse(ErrorResponse errorResponse) {
		return new ResponseEntity<>(errorResponse, errorResponse.getErrorCode().getHttpStatus());
	}
}
