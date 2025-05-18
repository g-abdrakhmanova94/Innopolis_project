package net.lesson5;

public class FizzBuzz {
    public static void main(String[] args) {

        for (int i = 1; i <= 100; i++) { // числа от 1 до 100

            if (i % 15 == 0) { // проверка числа, которое кратно 3 и 5
                System.out.println("FizzBuzz");

            } else if (i % 3 == 0) { // проверка числа, которое кратно только 3
                System.out.println("Fizz");

            } else if (i % 5 == 0) { // проверка числа, которое кратно только 5
                System.out.println("Buzz");

            } else { // если не подходит ни под одно условие, вывести просто переменную i
                System.out.println(i);
            }
        }
    }
}

