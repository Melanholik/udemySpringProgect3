package by.melanholik.weatherdata;


import by.melanholik.weatherdata.models.Sensor;
import by.melanholik.weatherdata.models.SensorData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class WeatherDataApplication {
    private final static String url = "http://api.weatherapi.com/v1/history.json?";
    private final static String key = "40008e7e281843a18bd125123232209";

    public static void main(String[] args) throws JsonProcessingException {
        get();
    }

    private static void get() throws JsonProcessingException {
        String[] cities = new String[]{"London", "Minsk", "Moscow", "Milan", "New York"};
        for (String city : cities) {
            getByTownAndSave(city);
        }
    }

    private static void getByTownAndSave(String nameTown) throws JsonProcessingException {
        addSensor(new Sensor(nameTown + " Weather"));
        RestTemplate restTemplate = new RestTemplate();
        LocalDate localDate = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            String currentUrl = url + "key=" + key + "&q=" + nameTown + "&dt=" + localDate;
            String str = restTemplate.getForObject(currentUrl, String.class);
            parseResult(str);
            localDate = localDate.minusDays(1);
        }
    }

    private static void parseResult(String jsonForParse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode obj = mapper.readTree(jsonForParse);
        String name = obj.get("location").get("name").toString().replace("\"", "");
        JsonNode arrData = obj.get("forecast").get("forecastday").get(0).get("hour");
        List<SensorData> sensorsData = parseToSensorData(name, arrData);
        postDateSensor(sensorsData);
    }

    private static List<SensorData> parseToSensorData(String name, JsonNode arrData) {
        List<SensorData> sensorsData = new ArrayList<>();
        for (int i = 0; i < arrData.size(); i++) {
            SensorData sensorData = new SensorData();
            sensorData.getSensor().setName(name + " Weather");
            JsonNode element = arrData.get(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\"yyyy-MM-dd HH:mm\"");
            sensorData.setDateWeather(LocalDateTime.parse(element.get("time").toString(), formatter));
            sensorData.setValue(Float.parseFloat(element.get("temp_c").toString()));
            sensorData.setRaining(element.get("condition").get("text").toString().contains("rain"));
            sensorsData.add(sensorData);
        }
        return sensorsData;
    }

    private static void postDateSensor(List<SensorData> sensorsData) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements/add";
        for (SensorData sensorsDatum : sensorsData) {
            HttpEntity<SensorData> request = new HttpEntity<>(sensorsDatum);
            restTemplate.postForObject(url, request, Object.class);
        }
    }

    private static void addSensor(Sensor sensor) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/sensors/registration";
        HttpEntity<Sensor> request = new HttpEntity<>(sensor);
        restTemplate.postForObject(url, request, Object.class);
    }
}
