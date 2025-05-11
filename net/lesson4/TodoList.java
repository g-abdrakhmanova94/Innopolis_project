package net.lesson4;

public class TodoList {
    public static void main(String[] args) {
        String[] TodoList = {"Принять водные процедуры", "Сделать зарядку", "Позавтракать",
                "Пойти на прогулку с собакой", "Сесть за работу"};
        System.out.println("Список дел на сегодня: ");
        for (int i = 0; i < TodoList.length; i++) {
            System.out.println((i+1) + " " + TodoList[i]);

        }
    }
}
