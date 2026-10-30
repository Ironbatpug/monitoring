package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.exception.SensorNotFoundException;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final SensorRepository sensorRepository;
    private final KafkaTemplate<String, AlertDto> kafkaTemplate;

    public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
                        KafkaTemplate<String, AlertDto> kafkaTemplate) {
        this.alertRepository = alertRepository;
        this.sensorRepository = sensorRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Alert saveAlert(AlertDto alertDto) {
        var sensor = Optional.of(alertDto)
                .map(AlertDto::sensorId)
                .flatMap(sensorRepository::findById)
                .orElseThrow(() -> new SensorNotFoundException(alertDto.sensorId()));

        var alert = alertRepository.save(new Alert(
                alertDto.message(),
                alertDto.timestamp(),
                sensor
        ));

        kafkaTemplate.send("alerts", alertDto);

        return alert;
    }

    public Optional<AlertDto> findLastAlertBySensorId(UUID sensorId) {
        return alertRepository.findFirstBySensorIdOrderByTimestampDescIdDesc(sensorId)
                .map(alert -> new AlertDto(sensorId, alert.getMessage(), alert.getTimestamp()));
    }
}
