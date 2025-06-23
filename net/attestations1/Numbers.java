package net.attestations1;

public class Numbers {
    public static void main(String[] args) {

        int[] Numbers = {5, 0, 4, 1, 8, 9, 0, 11, 3};

        // Найдем максимум
        int max = Numbers[0];
        for (int num : Numbers) {
            if (num > max) {
                max = num;
            }
        }

        // Найдем второй максимум
        int secondMax = Integer.MIN_VALUE;
        for (int num : Numbers) {
            if (num != max && num > secondMax) {
                secondMax = num;
            }
        }

        System.out.println("Второй по величине элемент в массиве: " + secondMax);
    }
}

