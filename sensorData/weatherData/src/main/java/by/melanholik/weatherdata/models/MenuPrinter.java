package by.melanholik.weatherdata.models;

public class MenuPrinter {

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
}
