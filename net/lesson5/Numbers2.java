package net.lesson5;

import java.util.Scanner;

public class Numbers2 {

    public static void main(String[] args) {
        int a = new Scanner(System.in).nextInt();

        if (a % 2 == 0) { //проверяем, что число - четное.

            if (a % 4 == 0) { //проверяем, что число кратно четырем.
                System.out.println("Четное число. Кратно четырем.");

            } else { //если не кратно четырем, но число - четное.
            System.out.println("Четное число.");
        }
        } else { //проверяем, что число - нечетное.

            if (a % 3 == 0) { //проверяем, что число кратно трем.
                System.out.println("Нечетное число. Кратно трем.");

            } else { //если не кратно трем, но число - нечетное.
                System.out.println("Нечетное число.");
            }
    }
}
    }