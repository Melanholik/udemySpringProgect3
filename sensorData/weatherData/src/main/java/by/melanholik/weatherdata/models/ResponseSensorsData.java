package by.melanholik.weatherdata.models;

import java.util.List;

public class ResponseSensorsData {
    private List<SensorData> sensorData;

    public ResponseSensorsData() {
    }

    public List<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(List<SensorData> sensorData) {
        this.sensorData = sensorData;
    }
}
