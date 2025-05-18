package net.lesson5;

public class Card {
    private String number;
    private String date;
    private String cvv;
    private String pinCode;

    public Card(String number, String date, String cvv, String pinCode) {
        this.number = number;
        this.date = date;
        this.cvv = cvv;
        this.pinCode = pinCode;
    }

    public String getNumber() { // Getter для номера карты
    return number;
}

    public void printMaskedNumber() { // Метод для печати маскированного номера
        String last4 = number.substring(number.length() - 4);
        System.out.println("Номер карты с маской: " + "**** **** **** " + last4);
    }

    public void printNumberIfPinCorrect(String inputPin) { // Метод для проверки PIN и вывода полного номера
        if (pinCode.equals(inputPin)) {
            System.out.println("Номер карты: " + getNumber());
        } else {
            System.out.println("Неверный PIN-код");
        }
    }
}


