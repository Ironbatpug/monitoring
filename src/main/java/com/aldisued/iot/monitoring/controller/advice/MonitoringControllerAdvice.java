package com.aldisued.iot.monitoring.controller.advice;

import com.aldisued.iot.monitoring.exception.DuplicateSensorNameException;
import com.aldisued.iot.monitoring.exception.SensorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MonitoringControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(MonitoringControllerAdvice.class);

    @ExceptionHandler(DuplicateSensorNameException.class)
    public ProblemDetail handleMonitoringException(DuplicateSensorNameException exception) {
        log.warn("Duplicate sensor name rejected", exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(SensorNotFoundException.class)
    public ProblemDetail handleMonitoringException(SensorNotFoundException exception) {
        log.warn("Monitoring request rejected", exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception exception) {
        log.error("Unexpected request failure", exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "An unexpected error occurred");
    }
}
