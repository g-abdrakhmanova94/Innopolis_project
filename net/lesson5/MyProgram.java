package net.lesson5;

import java.util.Scanner;

public class MyProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Card myCard = new Card("4276567811345678", "11/25", "578", "1368");

        myCard.printMaskedNumber(); // вывод маскированного номера

        System.out.print("Введите PIN-код: ");
        String inputPin = scanner.nextLine();

        myCard.printNumberIfPinCorrect(inputPin); // вывода полного номера карты в соответствии с корректным PIN-кодом
    }
}