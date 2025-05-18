package net.lesson5;

public class Car {
    private String brand;
    private String model;
    private int currentSpeed = 0; // по умолчанию 0

    public Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }
    public int getCurrentSpeed() { // метод для получения текущей скорости
        return currentSpeed;
    }

    public void speedUp(int i) { // метод для увеличения скорости
        if (i > 0) {
            currentSpeed += i;
        }
    }

    public void brake() { // метод для снижения скорости
        currentSpeed -= 10;
        if (currentSpeed < 0) {
            currentSpeed = 0;
        }
    }
}
