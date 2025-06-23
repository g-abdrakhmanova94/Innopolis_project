package net.attestations1;

import java.util.Scanner;

public class Fence {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);// Добавление сканера для ввода данных пользователем

        // Ввод длины забора
        System.out.print("Введите длину забора в сантиметрах: ");
        int fenceLength = scanner.nextInt();

        // Константы
        int letters = 15;
        int spaces = 3;

        // Расчет длины надписи - признания в любви
        int lengthLetters = (letters / 3) * 62; // 15 букв делится на 3, остаток не учитывается, так как условие не
       // уточняет, длина букв на заборе = 310 см
        int lengthSpaces = spaces * 12; // Пробелы умножаются на 12 см, длина пробелов на заборе = 36 см

        int MessageLength = lengthLetters + lengthSpaces; // Суммарная длина надписи мальчика = 346 см

        //Проверка вместимости надписи на забор
        if (fenceLength >= MessageLength) {
            System.out.println("Длина забора вместит в себя надпись мальчика");
        } else {
            System.out.println("Длина забора не вместит в себя надпись мальчика");
        }
    }
}