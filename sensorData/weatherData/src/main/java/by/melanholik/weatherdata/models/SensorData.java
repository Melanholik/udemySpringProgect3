package by.melanholik.weatherdata.models;

import java.time.LocalDateTime;

public class SensorData {

    private Float value;

    private Boolean raining;

    private Sensor sensor;

    private LocalDateTime dateWeather;

    public SensorData() {
        sensor = new Sensor();
    }

    public SensorData(Float value, Boolean raining, Sensor sensor, LocalDateTime dateWeather) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.dateWeather = dateWeather;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getDateWeather() {
        return dateWeather;
    }

    public void setDateWeather(LocalDateTime dateWeather) {
        this.dateWeather = dateWeather;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor.getName() +
                ", dateWeather=" + dateWeather +
                '}';
    }
}
