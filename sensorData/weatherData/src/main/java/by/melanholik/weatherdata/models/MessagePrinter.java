package by.melanholik.weatherdata.models;

public class MessagePrinter {

    public void startMenu() {
        System.out.println("Введите чисто - что вы хотите сделать?");
        System.out.println("1) Зарегистрировать новый сенсор");
        System.out.println("2) Добавить данные");
        System.out.println("3) Получить актуальные данные температуры по городу");
        System.out.println("4) Построить график по данным датчика");
        System.out.print("5) Выйти\n");
    }

    public void newSensorName(){
        System.out.println("Хотите ввести другое название принтера");
        System.out.println("1) Да");
        System.out.print("2) Нет\n");
    }

    public void newSensorDate(){
        System.out.println("Хотите ввести значения повторно");
        System.out.println("1) Да");
        System.out.println("2) Нет");
    }

    public void menuTime(){
        System.out.println("Хотите получить данные:");
        System.out.println("1) За сегодня ");
        System.out.println("2) За прошедшую неделю");
    }

    public void isRaining(){
        System.out.println("Был ли дождь? (true/false)");
    }

    public void writeNameSensor(){
        System.out.println("Введите название сенсора");
    }

    public void writeNameCity(){
        System.out.println("Введите название города");
    }

    public void writeTemperature(){
        System.out.println("Введите Температуру");
    }

    public void writeLocalDataTime(){
        System.out.println("Введите дату в формате dd-MM-yyyy HH:mm");
    }

    public void mistakeAddSensor() {
        System.out.println("Произошла ошибка при добавления сенсора");
    }

    public void mistakeAddSensorData() {
        System.out.println("Произошла ошибка при добавлении данных сенсора");
    }
    public void addSensorDataSuccess() {
        System.out.println("Данные успешно добавились");
    }

}