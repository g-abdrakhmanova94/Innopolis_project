package net.package1;

public class Account {
    public static void main(String[] args) {
        String cardNumber = "1234 5678 9012 3456"; //можно передавать номер карты как с пробелами, так и без
        String cardNumber2 = cardNumber.replaceAll("\\s", ""); //удаление всех пробелов из строки и сохранение в переменную
        String cardNumber3 = cardNumber2.substring(cardNumber2.length()-4); //берутся последние 4 символа из строки и сохраняются в переменную
        System.out.println("**** **** **** " + cardNumber3); //вывод маскированного номера карты в консоль

    }
}
