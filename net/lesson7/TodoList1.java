package net.lesson7;

import java.util.ArrayList;
import java.util.List;

public class TodoList1 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(); // Создаем список строк

        // Добавляем в список 5 дел на день
        list.add("Купить корм коту");
        list.add("Сходить в тренажерный зал");
        list.add("Поработать");
        list.add("Приготовить ужин");
        list.add("Запустить стиральную машину");

        // Выводим список в нужном формате
        System.out.println("Список дел на сегодня: ");

        for (int i = 0; i < list.size(); i++) {
            System.out.println("Задача " +  (i + 1) + ": " + list.get(i));
        }
    }
}