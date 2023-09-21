package by.melanholik.sensordata.controllers;


import by.melanholik.sensordata.DTO.SensorDTO;
import by.melanholik.sensordata.Exceptions.ExceptionResponse;
import by.melanholik.sensordata.Exceptions.SensorIsAlreadyExistException;
import by.melanholik.sensordata.Exceptions.SensorIsNotCreatedException;
import by.melanholik.sensordata.model.Sensor;
import by.melanholik.sensordata.services.SensorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<Sensor> getAll() {
        return sensorService.getAll();
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

    @ExceptionHandler({SensorIsNotCreatedException.class, SensorIsAlreadyExistException.class})
    private ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        exceptionResponse.setTime(LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
