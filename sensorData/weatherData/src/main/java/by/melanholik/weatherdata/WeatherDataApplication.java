package by.melanholik.weatherdata;

import by.melanholik.weatherdata.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;


public class WeatherDataApplication {

    public static void main(String[] args) throws JsonProcessingException {
        JavaViewForClient view = new JavaViewForClient();
        view.startWork();
    }
}
