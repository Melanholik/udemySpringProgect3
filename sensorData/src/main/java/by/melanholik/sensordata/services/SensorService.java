package by.melanholik.sensordata.services;

import by.melanholik.sensordata.model.Sensor;
import by.melanholik.sensordata.repositories.SensorsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SensorService {

    private final SensorsRepositories sensorsRepositories;

    @Autowired
    public SensorService(SensorsRepositories sensorsRepositories) {
        this.sensorsRepositories = sensorsRepositories;
    }

    public List<Sensor> getAll() {
        return sensorsRepositories.findAll();
    }

    @Transactional
    public boolean add(Sensor sensor){
         if (sensorsRepositories.existsByName(sensor.getName())) {
             return false;
         }
         sensorsRepositories.save(sensor);
         return true;
    }
}
