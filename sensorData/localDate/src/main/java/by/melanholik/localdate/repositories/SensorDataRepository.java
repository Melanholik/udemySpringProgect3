package by.melanholik.localdate.repositories;

import by.melanholik.localdate.model.Sensor;
import by.melanholik.localdate.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
    Integer countByRainingIsTrue();

    List<SensorData> findByDateWeatherAndSensor(LocalDateTime dateWeather, Sensor sensor);
}
