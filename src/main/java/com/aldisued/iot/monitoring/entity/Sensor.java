package com.aldisued.iot.monitoring.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "sensors")
@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SensorType type;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alert> alerts = new ArrayList<>();

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SensorReading> sensorReadings = new ArrayList<>();

    public Sensor() {
    }

    public Sensor(String name, SensorType type) {
        this.name = name;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        List<Alert> replacements = List.copyOf(alerts);

        for (Alert alert : new ArrayList<>(this.alerts)) {
            if (!replacements.contains(alert)) {
                this.alerts.remove(alert);
                alert.setSensor(null);
            }
        }

        for (Alert alert : replacements) {
            if (!this.alerts.contains(alert)) {
                this.alerts.add(alert);
            }
            alert.setSensor(this);
        }
    }

    public List<SensorReading> getSensorReadings() {
        return sensorReadings;
    }

    public void setSensorReadings(List<SensorReading> sensorReadings) {
        List<SensorReading> replacements = List.copyOf(sensorReadings);

        for (SensorReading sensorReading : new ArrayList<>(this.sensorReadings)) {
            if (!replacements.contains(sensorReading)) {
                this.sensorReadings.remove(sensorReading);
                sensorReading.setSensor(null);
            }
        }

        for (SensorReading sensorReading : replacements) {
            if (!this.sensorReadings.contains(sensorReading)) {
                this.sensorReadings.add(sensorReading);
            }
            sensorReading.setSensor(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor sensor)) return false;
        return getId() != null && getId().equals(sensor.getId());
    }

    @Override
    public int hashCode() {
        return Sensor.class.hashCode();
    }
}
