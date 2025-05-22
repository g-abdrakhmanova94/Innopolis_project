package net.lesson7;

import java.util.ArrayList;
import java.util.List;

public class Companies {

    public static void main(String[] args) {

        // Создаем список компаний
        List<Company> companies = new ArrayList<>();

        // Создаем 3 компании
        Company company1 = new Company("Universal Pictures", "1912");
        Company company2 = new Company("Walt Disney Company", "1923");
        Company company3 = new Company("Paramount Pictures", "1912");

        // Добавляем по 3 фильма каждой компании
        company1.addMovie(new Movie1("Игры разума"));
        company1.addMovie(new Movie1("Список Шиндлера"));
        company1.addMovie(new Movie1("Третий лишний"));

        company2.addMovie(new Movie1("Зверополис"));
        company2.addMovie(new Movie1("Пираты Карибского моря"));
        company2.addMovie(new Movie1("Золушка"));

        company3.addMovie(new Movie1("Интерстеллар"));
        company3.addMovie(new Movie1("Форрест Гамп"));
        company3.addMovie(new Movie1("Остров проклятых"));

        // Добавляем компании в список
        companies.add(company1);
        companies.add(company2);
        companies.add(company3);

        // Выводим информацию
        for (Company c : companies) {
            System.out.println(c.getName() + ": " + c.getMovies().get(0).getName() + ", " +
                    c.getMovies().get(1).getName() + ", " +
                    c.getMovies().get(2).getName());
        }
    }
}
