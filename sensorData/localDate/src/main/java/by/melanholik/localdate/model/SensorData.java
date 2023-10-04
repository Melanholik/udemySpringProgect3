package by.melanholik.localdate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

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

    @ManyToOne()
    @JoinColumn(name = "sensor_id" , referencedColumnName = "id")
    @NotNull (message = "The sensor must not be null")
    @JsonBackReference
    private Sensor sensor;

    @NotNull
    @Column(name = "date_weather")
    private LocalDateTime dateWeather;

    @NotNull
    @Column(name = "creat_at")
    private LocalDateTime creatAt;

    public SensorData() {
    }

    public SensorData(Float value, Boolean raining, Sensor sensor, LocalDateTime dateWeather, LocalDateTime creatAt) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.dateWeather = dateWeather;
        this.creatAt = creatAt;
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

    public LocalDateTime getDateWeather() {
        return dateWeather;
    }

    public void setDateWeather(LocalDateTime dateWeather) {
        this.dateWeather = dateWeather;
    }

    public LocalDateTime getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(LocalDateTime creatAt) {
        this.creatAt = creatAt;
    }
}
