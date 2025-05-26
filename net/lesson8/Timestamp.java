package net.lesson8;

import java.time.Duration;
import java.time.LocalDateTime;

public class Timestamp implements HumanReadableTimestamp {

    @Override
    public String getTimestamp(LocalDateTime eventTimestamp) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(eventTimestamp, now);
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (minutes < 60) {
            // Меньше часа
            long x = minutes;
            if (x == 1 || (x % 10 == 1 && x != 11)) {
                return "опубликовано " + x + " минуту назад";
            } else if ((x >= 2 && x <= 4) || (x % 10 >= 2 && x % 10 <= 4 && !(x >= 12 && x <= 14))) {
                return "опубликовано " + x + " минуты назад";
            } else {
                return "опубликовано " + x + " минут назад";
            }
        } else if (hours < 24) {
            // Менее суток
            long x = hours;
            if (x == 1 || (x % 10 == 1 && x != 11)) {
                return "опубликовано " + x + " час назад";
            } else if ((x >= 2 && x <= 4) || (x % 10 >= 2 && x % 10 <= 4 && !(x >= 12 && x <= 14))) {
                return "опубликовано " + x + " часа назад";
            } else {
                return "опубликовано " + x + " часов назад";
            }
        } else if (days == 1) {
            // Вчера
            return "опубликовано вчера";
        } else {
            // Больше суток
            long x = days;
            if (x == 1 || (x % 10 == 1 && x != 11)) {
                return "опубликовано " + x + " день назад";
            } else if ((x >= 2 && x <= 4) || (x % 10 >= 2 && x % 10 <= 4 && !(x >= 12 && x <= 14))) {
                return "опубликовано " + x + " дня назад";
            } else {
                return "опубликовано " + x + " дней назад";
            }
        }
    }
}