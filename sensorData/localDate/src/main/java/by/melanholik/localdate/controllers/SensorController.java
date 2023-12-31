package by.melanholik.localdate.controllers;


import by.melanholik.localdate.DTO.SensorDTO;
import by.melanholik.localdate.Exceptions.SensorIsAlreadyExistException;
import by.melanholik.localdate.Exceptions.SensorIsNotCreatedException;
import by.melanholik.localdate.model.Sensor;
import by.melanholik.localdate.services.SensorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public List<SensorDTO> getAll() {
        return sensorService.getAll().stream()
                .map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/contains")
    public boolean isSensorContains(@RequestBody Sensor sensor) {
        return sensorService.isSensorExist(sensor);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorIsNotCreatedException(errorMsg.toString());
        }

        if (sensorService.add(convertToSensor(sensorDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new SensorIsAlreadyExistException("A sensor with the same name already exists");
    }


    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }


}
