package by.melanholik.weatherdata.Exception;

public class BadWeatherRequestException extends RuntimeException {
    public BadWeatherRequestException(String message) {
        super(message);
    }
}
