package net.lesson5;

public class MyProgram2 {
    public static void main(String[] args) {
        Car car1 = new Car("Mercedes-Benz", "AMG");

        System.out.println("Текущая скорость: " + car1.getCurrentSpeed()); // вывод текущей скорости

        car1.speedUp(25);
        System.out.println("Текущая скорость: " + car1.getCurrentSpeed()); // увеличение скорости на 25

        car1.brake();
        System.out.println("Текущая скорость: " + car1.getCurrentSpeed()); // снижение скорости, значение 15

        car1.brake();
        System.out.println("Текущая скорость: " + car1.getCurrentSpeed()); // снижение скорости, значение 5

        car1.brake();
        System.out.println("Текущая скорость: " + car1.getCurrentSpeed()); // снижение скорости, значение 0
    }
}
