package by.melanholik.weatherdata.models;

import by.melanholik.weatherdata.Exception.BadWeatherRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CityDateWeather {

    private final RestTemplate restTemplate;

    public CityDateWeather() {
        restTemplate = new RestTemplate();
    }

    public List<SensorData> getSensorsDataToday(String nameCity) throws JsonProcessingException {
        String jsonDataWeather = getResultByDay(nameCity, LocalDate.now());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode arrData = mapper.readTree(jsonDataWeather).get("forecast").get("forecastday").get(0).get("hour");
        return new ArrayList<>(parseToSensorData(nameCity, arrData));
    }

    public List<SensorData> getSensorsDataByLastWeak(String nameCity) throws JsonProcessingException {
        List<SensorData> sensorsData = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            String jsonDataWeather = getResultByDay(nameCity, localDate);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode arrData = mapper.readTree(jsonDataWeather).get("forecast").get("forecastday").get(0).get("hour");
            sensorsData.addAll(parseToSensorData(nameCity, arrData));
            localDate = localDate.minusDays(1);
        }
        return sensorsData;
    }


    private String getResultByDay(String nameCity, LocalDate localDate) {
        try {
            String key = "40008e7e281843a18bd125123232209";
            String url = "http://api.weatherapi.com/v1/history.json?";
            String currentUrl = url + "key=" + key + "&q=" + nameCity + "&dt=" + localDate;
            return restTemplate.getForObject(currentUrl, String.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadWeatherRequestException(e.getMessage());
        }
    }

    private List<SensorData> parseToSensorData(String nameCity, JsonNode arrData) {
        List<SensorData> sensorsData = new ArrayList<>();
        for (int i = 0; i < arrData.size(); i++) {
            SensorData sensorData = new SensorData();
            sensorData.getSensor().setName(nameCity + " Weather");
            JsonNode element = arrData.get(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\"yyyy-MM-dd HH:mm\"");
            sensorData.setDateWeather(LocalDateTime.parse(element.get("time").toString(), formatter));
            sensorData.setValue(Float.parseFloat(element.get("temp_c").toString()));
            sensorData.setRaining(element.get("condition").get("text").toString().contains("rain"));
            sensorsData.add(sensorData);
        }
        return sensorsData;
    }
}
