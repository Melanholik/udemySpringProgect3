package by.melanholik.localdate.repositories;

import by.melanholik.localdate.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorsRepositories extends JpaRepository<Sensor, Integer> {

    boolean existsByName(String name);

    Optional<Sensor> findByName(String name);
}
