package by.melanholik.sensordata.services;

import by.melanholik.sensordata.Exceptions.NotFoundSensorException;
import by.melanholik.sensordata.model.Sensor;
import by.melanholik.sensordata.model.SensorData;
import by.melanholik.sensordata.repositories.SensorDataRepository;
import by.melanholik.sensordata.repositories.SensorsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<Sensor> sensor = sensorsRepositories.findByName(sensorData.getSensor().getName());
        if (sensor.isEmpty()) {
            throw new NotFoundSensorException("Not found sensor with that name");
        }
        sensorData.setSensor(sensor.get());
        sensorDataRepository.save(sensorData);
    }

    public List<SensorData> getAll() {
        return sensorDataRepository.findAll();
    }


    public Integer rainingDays(){
        return sensorDataRepository.countByRainingIsTrue();
    }
}
