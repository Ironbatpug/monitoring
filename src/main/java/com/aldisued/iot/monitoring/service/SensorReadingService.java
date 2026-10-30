package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.exception.SensorNotFoundException;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;
    private final SensorRepository sensorRepository;

    public SensorReadingService(SensorReadingRepository sensorReadingRepository,
                                SensorRepository sensorRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public SensorReading saveSensorReading(SensorReadingDto sensorReadingDto) {
        var sensor = Optional.of(sensorReadingDto)
                .map(SensorReadingDto::sensorId)
                .flatMap(sensorRepository::findById)
                .orElseThrow(() -> new SensorNotFoundException(sensorReadingDto.sensorId()));

        return sensorReadingRepository.save(new SensorReading(
                sensorReadingDto.value(),
                sensorReadingDto.timestamp(),
                sensor
        ));
    }

}
