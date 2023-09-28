package by.melanholik.sensordata.controllers;

import by.melanholik.sensordata.DTO.SensorDTO;
import by.melanholik.sensordata.DTO.SensorDataDTO;
import by.melanholik.sensordata.Exceptions.SensorDataIsAlreadyExistException;
import by.melanholik.sensordata.model.Sensor;
import by.melanholik.sensordata.model.SensorData;
import by.melanholik.sensordata.services.SensorDataService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class SensorDataController {

    private final SensorDataService sensorDataService;
    private final ModelMapper modelMapper;

    public SensorDataController(SensorDataService sensorDataService, ModelMapper modelMapper) {
        this.sensorDataService = sensorDataService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<SensorDataDTO> getAll() {
        return sensorDataService.getAll().stream()
                .map(this::convertToSensorDataDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/byName")
    public List<SensorDataDTO> getByName(@RequestBody() SensorDTO sensor) {
        return sensorDataService.getAllByNameSensor(sensor).stream()
                .map(this::convertToSensorDataDTO)
                .collect(Collectors.toList());
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody SensorDataDTO sensorDataDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorDataIsAlreadyExistException(errorMsg.toString());
        }
        sensorDataService.add(convertToSensorData(sensorDataDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Integer getCountRainingDay() {
        return sensorDataService.rainingDays();
    }

    private SensorData convertToSensorData(SensorDataDTO sensorDataDTO) {
        SensorData sensorData = modelMapper.map(sensorDataDTO, SensorData.class);
        sensorData.setSensor(new Sensor(sensorDataDTO.getSensor().getName()));
        return sensorData;
    }

    private SensorDataDTO convertToSensorDataDTO(SensorData sensorData) {
        SensorDataDTO sensorDataDTO = modelMapper.map(sensorData, SensorDataDTO.class);
        sensorDataDTO.setSensor(new SensorDTO(sensorData.getSensor().getName()));
        return sensorDataDTO;
    }
}
