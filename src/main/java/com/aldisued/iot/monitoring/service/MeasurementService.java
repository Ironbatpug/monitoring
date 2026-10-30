package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class MeasurementService {

    private final SensorReadingRepository sensorReadingRepository;

    public MeasurementService(SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
    }

    public List<Double> getMeasurementValuesBySensorType(@NotNull SensorType sensorType, @NotNull LocalDateTime from,
                                                         @NotNull LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From date must be before to date");
        }

        return sensorReadingRepository.getMeasurementValuesBySensorType(sensorType, from, to);
    }

    public Optional<Double> getAverageTemperature(@NotNull LocalDateTime from, @NotNull LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From date must be before to date");
        }

        return sensorReadingRepository.calculateAverageByTypeBetweenFromAndTo(SensorType.TEMPERATURE, from, to);
    }

}
