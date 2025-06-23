package net.attestations1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Cactus {
        public static void main(String[] args) {
            LocalDate lastWatering = LocalDate.of(2025, 6, 20); // дата последнего полива
            LocalDate today = LocalDate.now();

            // Определим сезон
            int month = today.getMonthValue();
            String season;
            if (month == 12 || month <= 2) {
                season = "Winter";
            } else if (month >= 3 && month <= 5) {
                season = "Spring";
            } else if (month >= 6 && month <= 8) {
                season = "Summer";
            } else {
                season = "Autumn";
            }

            // Получим влажность
            int humidity = new Random().nextInt(100);

            // Решим, нужно ли полить
            boolean needWater = false;
            if (season.equals("Winter")) {
                needWater = ChronoUnit.DAYS.between(lastWatering, today) >= 30;
            } else if (season.equals("Spring") || season.equals("Autumn")) {
                needWater = ChronoUnit.DAYS.between(lastWatering, today) >= 7;
            } else if (season.equals("Summer")) {
                if (humidity < 30) {
                    needWater = ChronoUnit.DAYS.between(lastWatering, today) >= 2;
                }
            }

            // Вывод результата
            if (needWater) {
                System.out.println("Пора поливать кактус! Сегодня: " + today);
            } else {
                System.out.println("Поливать не нужно. Следующий раз через " +
                        (needWater ? 0 : (season.equals("Winter") ? 30 : (season.equals("Spring")
                                || season.equals("Autumn") ? 7 : 2))) + " дней");
            }
            System.out.println("Текущая влажность воздуха: " + humidity + "%");
        }
    }

