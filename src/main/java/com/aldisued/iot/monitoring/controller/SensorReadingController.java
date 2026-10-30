package com.aldisued.iot.monitoring.controller;

import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.dto.SensorReadingResponseDto;
import com.aldisued.iot.monitoring.service.SensorReadingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensor-readings")
public class SensorReadingController {

    private final SensorReadingService sensorReadingService;

    public SensorReadingController(SensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    @PostMapping
    public ResponseEntity<SensorReadingResponseDto> saveSensorReading(@RequestBody @Valid SensorReadingDto sensorReadingDto) {
        var sensorReading = sensorReadingService.saveSensorReading(sensorReadingDto);
        return ResponseEntity.ok(new SensorReadingResponseDto(sensorReading.getId(), sensorReading.getSensor().getId(),
                sensorReading.getValue(), sensorReading.getTimestamp()
        ));
    }
}
