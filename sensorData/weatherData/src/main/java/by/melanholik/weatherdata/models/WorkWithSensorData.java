package by.melanholik.weatherdata.models;

import by.melanholik.weatherdata.Exception.AddSensorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class WorkWithSensorData {

    private final RestTemplate restTemplate;

    public WorkWithSensorData() {
        restTemplate = new RestTemplate();
    }

    public void addDatesSensor(List<SensorData> sensorsData) {
        if (sensorsData.size() > 0) {
            String url = "http://localhost:8080/sensors/contains";
            Sensor sensor = sensorsData.get(0).getSensor();
            HttpEntity<Sensor> request = new HttpEntity<>(sensor);
            Boolean isHave = restTemplate.postForObject(url, request, Boolean.class);
            if (Boolean.FALSE.equals(isHave)) {
                addSensor(sensor);
            }
        }
        for (SensorData sensorData : sensorsData) {
            addDateSensor(sensorData);
        }
    }

    public void addDateSensor(SensorData sensorsData) {
        String url = "http://localhost:8080/measurements/add";
        HttpEntity<SensorData> request = new HttpEntity<>(sensorsData);
        restTemplate.postForObject(url, request, Object.class);
    }

    public void addSensor(Sensor sensor) {
        try {
            String url = "http://localhost:8080/sensors/registration";
            HttpEntity<Sensor> request = new HttpEntity<>(sensor);
            restTemplate.postForObject(url, request, Object.class);
        } catch (Exception e) {
            throw new AddSensorException(e.getMessage());
        }

    }

    public List<SensorData> getSensorsDataBySensorName(String name) {
        String url = "http://localhost:8080/measurements/byName";
        HttpEntity<Sensor> request = new HttpEntity<>(new Sensor(name));
        String jsonSensorData = restTemplate.postForObject(url, request, String.class);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            SensorData[] sensorData = objectMapper.readValue(jsonSensorData, SensorData[].class);
            return new ArrayList<>(List.of(sensorData));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
