package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.DuplicateSensorNameException;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public Sensor saveSensor(SensorDto sensor) {
        if (sensorRepository.existsByName(sensor.name())) {
            throw new DuplicateSensorNameException();
        }

        try {
            return sensorRepository.saveAndFlush(new Sensor(
                    sensor.name(),
                    sensor.type()
            ));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateSensorNameException();
        }
    }
}
