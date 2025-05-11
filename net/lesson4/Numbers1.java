package net.lesson4;

public class Numbers1 {
    public static void main(String[] args) {
        float[] numbers1 = new float[3];
        numbers1[0] = Math.round(Math.PI * 100000.0f) / 100000.0f;
        numbers1[1] = Math.round(Math.E * 100000.0f) / 100000.0f;
        numbers1[2] = 1.00000f;
        System.out.println("Значение чисел: ");
        for (int i = 0; i < numbers1.length; i++) {
            System.out.println(numbers1[i]);
        }
    }
}
