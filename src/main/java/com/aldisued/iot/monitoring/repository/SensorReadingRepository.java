package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    @Query("""
            SELECT AVG(sr.value)
            FROM SensorReading sr
                    join sr.sensor s
            WHERE s.type = :type
              AND sr.timestamp >= :from
              AND sr.timestamp <= :to
            """
    )
    Optional<Double> calculateAverageByTypeBetweenFromAndTo(SensorType type, LocalDateTime from, LocalDateTime to);

    @Query("""
            SELECT sr.value
            FROM SensorReading sr
                    join sr.sensor s
            WHERE s.type = :type
              AND sr.timestamp >= :from
              AND sr.timestamp <= :to
            order by sr.timestamp asc
            """
    )
    List<Double> getMeasurementValuesBySensorType(SensorType type, LocalDateTime from, LocalDateTime to);
}
