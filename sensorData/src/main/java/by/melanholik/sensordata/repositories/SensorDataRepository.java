package by.melanholik.sensordata.repositories;

import by.melanholik.sensordata.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
}
