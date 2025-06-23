package net.attestations1;

import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int userScore = 0, progScore = 0;
        String[] options = {"К", "Н", "Б"};

        for (int i = 0; i < 5; i++) {
            System.out.println("Раунд " + (i+1) + ": Введите К, Н или Б:");
            String user = scanner.nextLine().toUpperCase();
            if (!user.equals("К") && !user.equals("Н") && !user.equals("Б")) {
                System.out.println("Некорректный ввод, повторите.");
                i--; continue;
            }

            String prog = options[random.nextInt(3)];
            System.out.println("Программа: " + prog);

            int res = 0;
            if (user.equals(prog)) {
                res = 0;
            } else if ((user.equals("К") && prog.equals("Н")) ||
                    (user.equals("Н") && prog.equals("Б")) ||
                    (user.equals("Б") && prog.equals("К"))) {
                res = 1; // пользователь выиграл
            } else {
                res = -1; // программа выиграла
            }

            int points = 0;
            if (res == 1) {
                if (user.equals("К")) points = 1;
                else if (user.equals("Н")) points = 2;
                else points = 5;
                userScore += points;
                System.out.println("Вы выиграли раунд! + " + points + " очков");
            } else if (res == -1) {
                if (prog.equals("К")) points = 1;
                else if (prog.equals("Н")) points = 2;
                else points = 5;
                progScore += points;
                System.out.println("Победила программа! + " + points + " очков");
            } else {
                System.out.println("Ничья");
            }
            System.out.println("Счет: Вы - " + userScore + ", Программа - " + progScore + "\n");
        }

        System.out.println("Игра окончена!");
        if (userScore > progScore) System.out.println("Вы победили!");
        else if (userScore < progScore) System.out.println("Победила программа!");
        else System.out.println("Ничья!");
        scanner.close();
    }
}
