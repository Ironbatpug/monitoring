package com.aldisued.iot.monitoring.messaging;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.service.AlertService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AlertKafkaListener {
  private final AlertService alertService;
  private final Validator validator;

  public AlertKafkaListener(AlertService alertService, Validator validator) {
    this.alertService = alertService;
    this.validator = validator;
  }

  @KafkaListener(topics = {"sensor-alerts"}, groupId = "iot-monitoring")
  public void listen(AlertDto alertDto) {
    var violations = validator.validate(alertDto);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
    alertService.saveAlert(alertDto);
  }

}
