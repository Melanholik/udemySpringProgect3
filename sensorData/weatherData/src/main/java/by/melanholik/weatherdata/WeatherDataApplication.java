package by.melanholik.weatherdata;


import by.melanholik.weatherdata.models.CityDateWeather;
import by.melanholik.weatherdata.models.SensorData;
import by.melanholik.weatherdata.models.WorkWithSensorData;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.util.List;


public class WeatherDataApplication {

    public static void main(String[] args) throws JsonProcessingException {
        String[] cities = new String[]{"London", "Minsk", "Moscow", "Milan", "New York", "Paris"};
        WorkWithSensorData workWithSensorData = new WorkWithSensorData();
        for (String city : cities) {
            CityDateWeather cityDateWeather = new CityDateWeather(city, LocalDate.now());
            List<SensorData> sensorData = cityDateWeather.getSensorsDataByLastWeak();
            workWithSensorData.addSensor(workWithSensorData.creatSensorByCityName(city));
            workWithSensorData.postDatesSensor(sensorData);
        }
    }
}
