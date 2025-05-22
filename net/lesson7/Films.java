package net.lesson7;

import java.util.ArrayList;
import java.util.List;

public class Films {
    public static void main(String[] args) {

        List<Movie> films = new ArrayList<>(); // Создаем список фильмов

        // Добавляем в список 3 любимых фильма
        films.add (new Movie("Криминальное чтиво", 9, "Криминал", "США", true));
        films.add (new Movie("Крестный отец", 9, "Драма", "США", true));
        films.add (new Movie("Побег из Шоушенка", 9, "Триллер", "США", true));

        // Выводим информацию о фильме по индексу 1
        System.out.println(films.get(1).toString());
    }
}
