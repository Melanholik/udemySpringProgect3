package by.melanholik.weatherdata.models;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class JavaViewForClient {
    private int choice;
    private final MessagePrinter messagePrinter;
    private final Scanner scanner;
    private final WorkWithSensorData workWithSensorData;
    private final CityDateWeather cityDateWeather;

    public JavaViewForClient() {
        choice = 0;
        messagePrinter = new MessagePrinter();
        scanner = new Scanner(System.in);
        workWithSensorData = new WorkWithSensorData();
        cityDateWeather = new CityDateWeather();
    }

    public void startWork() {
        while (choice != 5) {
            messagePrinter.startMenu();
            choice = getIntByScanner();
            if (choice > 5 || choice < 1) {
                badChoice();
            } else {
                switchChoice(choice);
            }
        }
    }

    private void switchChoice(int choice) {
        switch (choice) {
            case (1) -> addNewSensor();
            case (2) -> addNewSensorData();
            case (3) -> System.out.println("Вы выбрали третий пункт");
            case (4) -> System.out.println("Вы выбрали четвертый пункт");
        }
    }

    private void addNewSensor() {
        messagePrinter.writeNameSensor();
        String sensorName = getNextLineByScanner();
        Sensor sensor = new Sensor(sensorName);
        try {
            workWithSensorData.addSensor(sensor);
            System.out.println("Сенсор успешно добавлен");
        } catch (Exception e) {
            messagePrinter.mistakeAddSensor();
            System.out.println(e.getMessage());
            messagePrinter.newSensorName();
            int choice = getIntByScanner();
            if (choice == 1) {
                addNewSensor();
            }
        }
    }

    private void getSensorDataByCityName() throws JsonProcessingException {
        messagePrinter.writeNameCity();
        String nameCity = getNextLineByScanner();
        messagePrinter.menuTime();
        int choice = getIntByScanner();
        List<SensorData> sensorData;
        if (choice == 1) {
            sensorData = cityDateWeather.getSensorsDataToday(nameCity);
        }
    }

    private void addNewSensorData() {
        SensorData sensorData = createSensorData();
        postSensorData(sensorData);
    }

    private SensorData createSensorData() {
        SensorData sensorData = new SensorData();
        messagePrinter.writeNameSensor();
        sensorData.setSensor(new Sensor(getNextLineByScanner()));
        messagePrinter.writeTemperature();
        sensorData.setValue((float) getDoubleByScanner());
        messagePrinter.isRaining();
        sensorData.setRaining(getBooleanByScanner());
        messagePrinter.writeLocalDataTime();
        sensorData.setDateWeather(getLocalDataTimeByScanner());
        return sensorData;
    }

    private void postSensorData(SensorData sensorData) {
        try {
            workWithSensorData.postDateSensor(sensorData);
            messagePrinter.addSensorDataSuccess();
        } catch (Exception e) {
            messagePrinter.mistakeAddSensorData();
            System.out.println(e.getMessage());
            messagePrinter.newSensorDate();
            if (getIntByScanner() == 1) {
                addNewSensor();
            }
        }
    }

    private int getIntByScanner() {
        int number;
        try {
            number = scanner.nextInt();
            return number;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.print("Вы ввели не число! Повторите ввод:\n");
            return getIntByScanner();
        }
    }

    private String getNextLineByScanner() {
        try {
            scanner.nextLine();
            return scanner.nextLine();
        } catch (Exception e) {
            System.out.print("Вы что-то ввели неправильно! Повторите ввод:\n");
            scanner.nextLine();
            return getNextLineByScanner();
        }
    }

    private double getDoubleByScanner() {
        double number;
        try {
            number = scanner.nextDouble();
            return number;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.print("Вы ввели не число! Повторите ввод:\n");
            return getDoubleByScanner();
        }
    }

    private boolean getBooleanByScanner() {
        boolean number;
        try {
            number = scanner.nextBoolean();
            return number;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.print("Вы ввели не true/false! Повторите ввод:\n");
            return getBooleanByScanner();
        }
    }

    private LocalDateTime getLocalDataTimeByScanner() {
        String str;
        try {
            scanner.nextLine();
            str = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return LocalDateTime.parse(str, formatter);
        } catch (Exception e) {
            scanner.nextLine();
            System.out.print("Вы ввели дату не в формате dd-MM-yyyy HH:mm! Повторите ввод:\n");
            return getLocalDataTimeByScanner();
        }
    }

    private void badChoice() {
        System.out.println("Число должно быть в диапазоне от 1 до 5");
        messagePrinter.startMenu();
    }

}