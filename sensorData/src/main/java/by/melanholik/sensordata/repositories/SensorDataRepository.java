package by.melanholik.sensordata.repositories;

import by.melanholik.sensordata.model.Sensor;
import by.melanholik.sensordata.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
    Integer countByRainingIsTrue();

    List<SensorData> findByDateWeatherAndSensor(LocalDateTime dateWeather, Sensor sensor);
}
