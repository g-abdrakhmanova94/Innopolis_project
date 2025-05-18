package net.lesson5;

import java.util.Scanner;

public class Password {

    public static void main(String[] args) {
        String password = new Scanner(System.in).nextLine();

        if (password.length() < 8) { //Проверка длины пароля
            System.out.println("Пароль не менее 8 символов");
        }
        // Использование метода matches () с регулярным выражением

        else if (password.matches(".*\\d.*") == false) { //Проверка содержания в пароле цифры
            System.out.println("Пароль должен содержать минимум 1 цифру");
        }

        else if (password.matches(".*[!@#$%^&*№].*") == false) { //Проверка содержания в пароле определенного спецсимвола
            System.out.println("Пароль должен содержать минимум 1 спецсимвол");
        }

        else { //Если пароль подходит по всем критериям
            System.out.println("Пароль принят");
        }
    }
}
