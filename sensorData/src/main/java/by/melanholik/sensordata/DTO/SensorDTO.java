package by.melanholik.sensordata.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotBlank
    @NotEmpty(message = "Name must be not empty")
    @NotNull(message = "Name must be not null")
    @Size(min = 3, max = 30, message = "Length sensor must be between 3 and 30 symbol")
    private String name;

    public SensorDTO() {
    }

    public SensorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
