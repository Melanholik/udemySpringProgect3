package by.melanholik.weatherdata.models;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class WorkWithSensorData {

    private RestTemplate restTemplate = new RestTemplate();

    public void postDatesSensor(List<SensorData> sensorsData) {
        for (SensorData sensorData : sensorsData) {
            postDateSensor(sensorData);
        }
    }

    public void postDateSensor(SensorData sensorsData) {
        restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements/add";
        HttpEntity<SensorData> request = new HttpEntity<>(sensorsData);
        restTemplate.postForObject(url, request, Object.class);
    }

    public void addSensor(Sensor sensor) {
        try {
            restTemplate = new RestTemplate();
            String url = "http://localhost:8080/sensors/registration";
            HttpEntity<Sensor> request = new HttpEntity<>(sensor);
            restTemplate.postForObject(url, request, Object.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Sensor creatSensorByCityName(String nameCity) {
        return new Sensor(nameCity + " Weather");
    }

}
