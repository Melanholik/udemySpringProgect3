package by.melanholik.sensordata.DTO;

import by.melanholik.sensordata.model.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SensorDataDTO {

    @NotNull(message = "The value must not be null")
    @Min(value = -100, message = "The value must be great than -100")
    @Max(value = 100, message = "The value must be less than 100")
    private Float value;

    @NotNull(message = "The value of raining must not be null")
    private Boolean raining;

    @NotNull(message = "The sensor must not be null")
    private Sensor sensor;

    public SensorDataDTO() {
    }

    public SensorDataDTO(Float value, Boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
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
}
