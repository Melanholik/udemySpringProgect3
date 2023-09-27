package by.melanholik.weatherdata.models;

import java.util.Scanner;

public class JavaViewForClient {
    private int choice;
    private final MenuPrinter menuPrinter;
    private final Scanner scanner;

    public JavaViewForClient() {
        choice = 0;
        menuPrinter = new MenuPrinter();
        scanner = new Scanner(System.in);
    }

    public void startWork() {
        while (choice != 5) {
            menuPrinter.startMenu();
            choice = getIntByScanner();
            if (choice > 5 || choice < 1) {
                badChoice();
            } else {
                switchChoice(choice);
            }
        }
    }

    private void badChoice() {
        System.out.println("Число должно быть в диапазоне от 1 до 5");
        menuPrinter.startMenu();
    }

    private void switchChoice(int choice) {
        switch (choice) {
            case (1):
                addNewSensor();
                break;
            case (2):
                System.out.println("Вы выбрали второй пункт");
                break;
            case (3):
                System.out.println("Вы выбрали третий пункт");
                break;
            case (4):
                System.out.println("Вы выбрали четвертый пункт");
                break;
        }
    }

    private void addNewSensor() {
        System.out.println("Введите название сенсора");
        String sensorName = getNextLineByScanner();
        Sensor sensor = new Sensor(sensorName);
        WorkWithSensorData workWithSensorData = new WorkWithSensorData();
        try {
            workWithSensorData.addSensor(sensor);
            System.out.println("Сенсор успешно добавлен");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при создании сенсора:");
            System.out.println(e.getMessage());
            menuPrinter.newSensorName();
            int choice = getIntByScanner();
            if (choice == 1) {
                addNewSensor();
            }
        }
    }

    private int getIntByScanner() {
        int number = 0;
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
        String str = "";
        try {
            scanner.nextLine();
            str = scanner.nextLine();
            return str;
        } catch (Exception e) {
            System.out.print("Вы что-то ввели неправильно! Повторите ввод:");
            scanner.nextLine();
            return getNextLineByScanner();
        }
    }
}