package by.melanholik.sensordata.services;

import by.melanholik.sensordata.DTO.SensorDTO;
import by.melanholik.sensordata.Exceptions.NotFoundSensorException;
import by.melanholik.sensordata.model.Sensor;
import by.melanholik.sensordata.model.SensorData;
import by.melanholik.sensordata.repositories.SensorDataRepository;
import by.melanholik.sensordata.repositories.SensorsRepositories;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final SensorsRepositories sensorsRepositories;

    @Autowired
    public SensorDataService(SensorDataRepository sensorDataRepository, SensorsRepositories sensorsRepositories) {
        this.sensorDataRepository = sensorDataRepository;
        this.sensorsRepositories = sensorsRepositories;
    }

    @Transactional
    public void add(SensorData sensorData) {
        addParamToSensorData(sensorData);
        Optional<SensorData> currentSensorDate = findByDateWeatherAndSensor(sensorData.getDateWeather(),
                sensorData.getSensor());
        currentSensorDate.ifPresent(data -> sensorData.setId(data.getId()));
        sensorDataRepository.save(sensorData);
    }

    public List<SensorData> getAll() {
        return sensorDataRepository.findAll();
    }

    @Transactional
    public List<SensorData> getAllByNameSensor(SensorDTO sensor) {
        Optional<Sensor> currentSensor = sensorsRepositories.findByName(sensor.getName());
        if (currentSensor.isPresent()) {
            return currentSensor.get().getSensorData();
        } else return Collections.emptyList();
    }

    public Integer rainingDays() {
        return sensorDataRepository.countByRainingIsTrue();
    }

    private Optional<SensorData> findByDateWeatherAndSensor(LocalDateTime dateWeather, Sensor sensor) {
        List<SensorData> sensorsData = sensorDataRepository.findByDateWeatherAndSensor(dateWeather, sensor);
        if (sensorsData.size() > 0) {
            return Optional.of(sensorsData.get(0));
        }
        return Optional.empty();
    }

    private void addParamToSensorData(SensorData sensorData) {
        Optional<Sensor> sensor = sensorsRepositories.findByName(sensorData.getSensor().getName());
        if (sensor.isEmpty()) {
            throw new NotFoundSensorException("Not found sensor with that name");
        }
        sensorData.setCreatAt(LocalDateTime.now());
        sensorData.setSensor(sensor.get());
    }
}
