package net.lesson4;

public class Films {
    public static void main(String[] args) {
        Movie[] films = {
                new Movie("Криминальное чтиво", 8, "Криминал", "США", true, "1994"),
                new Movie("Энни Холл", 7, "Мелодрама", "США", true, "1977"),
                new Movie("Большой Лебовски", 7, "Комедия", "Великобритания", false, "1998"),
        };
        System.out.println("Любимые фильмы: ");
        for (int i = 0; i < films.length; i++) {
            System.out.println((i + 1) + ": " + "Название: " + films[i].name + ", " + "Рейтинг: " + films[i].rating + ", " +
                    "Жанр: " + films[i].genre + ", " + "Страна: " + films[i].country + ", " + "Оскар: " + (films[i].isOscar ? "Да" : "Нет")
            );
        }
    }
}
