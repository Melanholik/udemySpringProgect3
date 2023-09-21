package by.melanholik.sensordata.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sensor_data")
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    @NotNull(message = "The value must not be null")
    @Min(value = -100, message = "The value must be great than -100")
    @Max(value = 100, message = "The value must be less than 100")
    private Float value;

    @Column(name = "raining")
    @NotNull (message = "The value of raining must not be null")
    private Boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id" , referencedColumnName = "id")
    @NotNull (message = "The sensor must not be null")
    private Sensor sensor;

    public SensorData() {
    }

    public SensorData(Float value, Boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setRaining(Boolean reining) {
        this.raining = reining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
