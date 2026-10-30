package com.aldisued.iot.monitoring.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SensorReadingDto(
        @NotNull(message = "sensorId must not be null")
        UUID sensorId,
        @NotNull(message = "value must not be null")
        Double value,
        @NotNull(message = "timestamp must not be null")
        LocalDateTime timestamp
) {
}
