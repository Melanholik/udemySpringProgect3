package by.melanholik.sensordata.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SensorDataDTO {

    @NotNull(message = "The value must not be null")
    @Min(value = -100, message = "The value must be great than -100")
    @Max(value = 100, message = "The value must be less than 100")
    private Float value;

    @NotNull(message = "The value of raining must not be null")
    private Boolean raining;

    @NotNull(message = "The sensor must not be null")
    private SensorDTO sensor;

    @NotNull
    private LocalDateTime dateWeather;

    public SensorDataDTO() {
    }

    public SensorDataDTO(Float value, Boolean raining, SensorDTO sensor, LocalDateTime dateWeather) {
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

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getDateWeather() {
        return dateWeather;
    }

    public void setDateWeather(LocalDateTime dateWeather) {
        this.dateWeather = dateWeather;
    }
}
