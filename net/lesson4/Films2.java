package net.lesson4;

public class Films2 {
    public static void main(String[] args) {
        Movie[] films2 = {
                new Movie("Криминальное чтиво", 8, "Криминал", "США", true, "1994"),
                new Movie("Энни Холл", 7, "Мелодрама", "США", true, "1977"),
                new Movie("Большой Лебовски", 7, "Комедия", "Великобритания", false, "1998"),
        };
        System.out.println("Любимые фильмы: ");
        for (int i = 0; i < films2.length; i++) {
            System.out.println(films2[i].year + " - " + films2[i].name + " - " +
                   films2[i].genre + " - " + films2[i].rating);
        }
    }
}
