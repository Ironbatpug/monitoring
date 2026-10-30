package com.aldisued.iot.monitoring.messaging;

import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.service.SensorReadingService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SensorReadingListener {
  private final SensorReadingService sensorReadingService;
  private final Validator validator;

  public SensorReadingListener(SensorReadingService sensorReadingService, Validator validator) {
    this.sensorReadingService = sensorReadingService;
    this.validator = validator;
  }

  @KafkaListener(topics = {"sensor-reading"}, groupId = "iot-monitoring")
  public void listen(SensorReadingDto sensorReadingDto) {
    var violations = validator.validate(sensorReadingDto);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
    sensorReadingService.saveSensorReading(sensorReadingDto);
  }

}
