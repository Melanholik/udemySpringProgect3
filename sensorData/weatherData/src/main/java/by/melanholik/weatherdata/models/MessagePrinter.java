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

    public void newSensorName() {
        System.out.println("Хотите ввести другое название принтера");
        System.out.println("1) Да");
        System.out.print("2) Нет\n");
    }

    public void newDate() {
        System.out.println("Хотите ввести значения повторно");
        System.out.println("1) Да");
        System.out.println("2) Нет");
    }

    public void menuTime() {
        System.out.println("Хотите получить данные:");
        System.out.println("1) За сегодня ");
        System.out.println("2) За прошедшую неделю");
    }

    public void menuSave() {
        System.out.println("Хотите сохранить данные:");
        System.out.println("1) Да");
        System.out.println("2) Нет");
    }

    public void isRaining() {
        System.out.println("Был ли дождь? (true/false)");
    }

    public void writeNameSensor() {
        System.out.println("Введите название сенсора");
    }

    public void writeNameCity() {
        System.out.println("Введите название города");
    }

    public void writeTemperature() {
        System.out.println("Введите Температуру");
    }

    public void writeLocalDataTime() {
        System.out.println("Введите дату в формате dd-MM-yyyy HH:mm");
    }

    public void mistakeAddSensor() {
        System.out.println("Произошла ошибка при добавления сенсора");
    }

    public void mistakeAddSensorData() {
        System.out.println("Произошла ошибка при добавлении данных сенсора");
    }

    public void mistakeGetData() {
        System.out.println("Произошла ошибка при получении данных");
    }

    public void addDataSuccess() {
        System.out.println("Данные успешно добавились");
    }

    public void addSensorSuccess() {
        System.out.println("Сенсор успешно добавились");
    }

    public void badRange(int min, int max) {
        System.out.println("Число должно быть в диапазоне от " + min + " до " + max);
    }

    public void exceptionNotFoundOrNoData() {
        System.out.println("Не найден сенсор или нет данных к нему");
    }

    public void exceptionNotNumber() {
        System.out.println("Вы ввели не число! Повторите ввод:\n");
    }

    public void exceptionBadEnter() {
        System.out.println("Вы что-то ввели неправильно! Повторите ввод:\n");
    }

    public void exceptionNoBoolean() {
        System.out.println("Вы ввели не true/false! Повторите ввод:\n");
    }

    public void exceptionNotDate() {
        System.out.println("Вы ввели дату не в формате dd-MM-yyyy HH:mm! Повторите ввод:\n");
    }
}
