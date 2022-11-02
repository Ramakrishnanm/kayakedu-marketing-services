package com.kayakedu.marketing.services.exception;

/**
 * Exception for all workflow business/known controllable error types.
 *
 * @author R.Manoharan
 */
@lombok.Getter
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 7263684210348341559L;

    private String operation;
    private ErrorScenario scenario;
    private String status;
    private String processInstanceId;

    /**
     * Constructs a new workflow service exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new workflow service exception with the specified detail message,
     * for specified service, and under said business scenario.
     *
     * @param message   the detail message.
     * @param operation the service name or the workflow operation identifier.
     * @param scenario  the failure cause type.
     */
    public ServiceException(String message, ErrorScenario scenario, String operation) {
        this(message);
        this.operation = operation;
        this.scenario = scenario;
    }
    
    /**
     * Constructs a new workflow service exception with the specified detail message,
     * for specified service, and current task of that request.
     * @param message
     * @param scenario
     * @param operation
     * @param processInstanceId
     */
    public ServiceException(String message, ErrorScenario scenario, String operation, String processInstanceId) {
        this(message);
        this.operation = operation;
        this.scenario = scenario;
        this.processInstanceId = processInstanceId;
    }
    
    /**
     * Constructs a new workflow service exception with the specified detail message,
     *  for specified service, and under said business scenario.
     *
     * @param message      the detail message.
     * @param operation    the service name or the Workflow operation identifier.
     * @param scenario     the failure cause type.
     * @param status       the error code from external-service/downstream-system.
     */
    public ServiceException(String message, String operation, ErrorScenario scenario,
    									String status) {
        this(message, scenario, operation);
        this.status = status;
    }

    /**
     * Constructs a new workflow service exception with the specified detail message,
     * for specified service, and under said business scenario.
     *
     * @param cause        the underlying cause.
     * @param operation    the service name or the Workflow operation identifier.
     * @param scenario     the failure cause type.
     * @param status       the error code from external-service/downstream-system.
    public WorkflowServiceException(Throwable cause, String operation, ErrorScenario scenario,
    									 String status) {
        this(Throwables.getRootCause(cause).getMessage(), operation, scenario, status);
        initCause(cause);
    }
     */

    /**
     * Constructs a new workflow service exception with the specified detail message and
     * cause.
     * <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

