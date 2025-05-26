package net.lesson8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Homework {
    public static void main(String[] args) {
        LocalDate publishDate = LocalDate.now().minusDays(1);
        LocalTime publishTime = LocalTime.now().minusHours(5);
        LocalDateTime timestamp = LocalDateTime.of(publishDate, publishTime);

        HumanReadableTimestamp hrTimestamp = new Timestamp();
        System.out.println(hrTimestamp.getTimestamp(timestamp));
    }
}
