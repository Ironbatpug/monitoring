package com.aldisued.iot.monitoring.exception;

public class DuplicateSensorNameException extends RuntimeException {

    public DuplicateSensorNameException() {
        super("Sensor with the same name already exists.");
    }
}
