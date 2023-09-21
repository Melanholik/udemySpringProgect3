package by.melanholik.sensordata.controllers;

import by.melanholik.sensordata.DTO.SensorDataDTO;
import by.melanholik.sensordata.Exceptions.ExceptionResponse;
import by.melanholik.sensordata.Exceptions.NotFoundSensorException;
import by.melanholik.sensordata.Exceptions.SensorDataIsAlreadyExistException;
import by.melanholik.sensordata.model.SensorData;
import by.melanholik.sensordata.services.SensorDataService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    private SensorData convertToSensorData(SensorDataDTO sensorDataDTO) {
        return modelMapper.map(sensorDataDTO, SensorData.class);
    }

    private SensorDataDTO convertToSensorDataDTO(SensorData sensorData) {
        return modelMapper.map(sensorData, SensorDataDTO.class);
    }

    @ExceptionHandler({SensorDataIsAlreadyExistException.class, NotFoundSensorException.class})
    private ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        exceptionResponse.setTime(LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
