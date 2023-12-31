package by.melanholik.weatherdata.models;

import by.melanholik.weatherdata.Exception.GetDataException;

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
            case (3) -> getSensorDataByCityName();
            case (4) -> watchSchedule();
        }
    }

    private void addNewSensor() {
        messagePrinter.writeNameSensor();
        String sensorName = getNextLineByScanner();
        Sensor sensor = new Sensor(sensorName);
        try {
            workWithSensorData.addSensor(sensor);
            messagePrinter.addSensorSuccess();
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

    private void getSensorDataByCityName() {
        messagePrinter.writeNameCity();
        String nameCity = getNextLineByScanner();
        messagePrinter.menuTime();
        try {
            List<SensorData> sensorData = getSensorsData(nameCity);
            System.out.println(sensorData);
            messagePrinter.menuSave();
            yesNoChoice(() -> workWithSensorData.addDatesSensor(sensorData), () -> {
            });
            messagePrinter.addDataSuccess();
        } catch (Exception e) {
            messagePrinter.mistakeGetData();
            System.out.println(e.getMessage());
        }
    }

    private void watchSchedule() {
        messagePrinter.writeNameSensor();
        String nameSensor = getNextLineByScanner();
        List<SensorData> sensorData = workWithSensorData.getSensorsDataBySensorName(nameSensor);
        if (sensorData.isEmpty()) {
            messagePrinter.exceptionNotFoundOrNoData();
        }
        Schedule.whiteTemCByTime(sensorData);
    }

    private void yesNoChoice(Method firstMethod, Method secondMethod) {
        int choice;
        while (true) {
            choice = getIntByScanner();
            if (choice == 1) {
                try {
                    firstMethod.doMethod();
                    break;
                } catch (Exception e) {
                    throw new GetDataException("Ошибка:\n" + e.getMessage());
                }
            } else if (choice == 2) {
                try {
                    secondMethod.doMethod();
                    break;
                } catch (Exception e) {
                    throw new GetDataException("Ошибка:\n" + e.getMessage());
                }
            }
            messagePrinter.badRange(1, 2);
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
            workWithSensorData.addDateSensor(sensorData);
            messagePrinter.addDataSuccess();
        } catch (Exception e) {
            messagePrinter.mistakeAddSensorData();
            System.out.println(e.getMessage());
            messagePrinter.newDate();
            if (getIntByScanner() == 1) {
                addNewSensor();
            }
        }
    }

    private List<SensorData> getSensorsData(String nameCity) {
        int choice = getIntByScanner();
        while (true) {
            if (choice == 1) {
                try {
                    return cityDateWeather.getSensorsDataToday(nameCity);
                } catch (Exception e) {
                    throw new GetDataException("Ошибка при получении данных\n" + e.getMessage());
                }
            } else if (choice == 2) {
                try {
                    return cityDateWeather.getSensorsDataByLastWeak(nameCity);
                } catch (Exception e) {
                    throw new GetDataException("Ошибка при получении данных\n" + e.getMessage());
                }
            }
            messagePrinter.badRange(1, 2);
        }
    }

    private int getIntByScanner() {
        int number;
        try {
            number = scanner.nextInt();
            return number;
        } catch (Exception e) {
            scanner.nextLine();
            messagePrinter.exceptionNotNumber();
            return getIntByScanner();
        }
    }

    private String getNextLineByScanner() {
        try {
            scanner.nextLine();
            return scanner.nextLine();
        } catch (Exception e) {
            messagePrinter.exceptionBadEnter();
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
            messagePrinter.exceptionNotNumber();
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
            messagePrinter.exceptionNoBoolean();
            return getBooleanByScanner();
        }
    }

    private LocalDateTime getLocalDataTimeByScanner() {
        String str;
        try {
            str = getNextLineByScanner();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return LocalDateTime.parse(str, formatter);
        } catch (Exception e) {
            messagePrinter.exceptionNotDate();
            return getLocalDataTimeByScanner();
        }
    }

    private void badChoice() {
        messagePrinter.badRange(1, 5);
    }

    private interface Method {
        void doMethod();
    }
}