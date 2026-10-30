package com.aldisued.iot.monitoring.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class MeasurementCalculatorService {

  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    // TODO: Task 9
    return List.of();
  }

  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
      if (windowSize <= 0) {
          throw new IllegalArgumentException("Window size must be positive");
      }
      if (windowSize > data.size()) {
          throw new IllegalArgumentException("Window size must not exceed the number of values");
      }

      return IntStream.rangeClosed(0, data.size() - windowSize)
              .mapToObj(start -> data.subList(start, start + windowSize).stream()
                      .mapToDouble(Double::doubleValue)
                      .average()
                      .orElseThrow())
              .toList();
  }

}
