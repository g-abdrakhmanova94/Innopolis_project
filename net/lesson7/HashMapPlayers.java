package net.lesson7;

import java.util.HashMap;
import java.util.Map;

public class HashMapPlayers {

    public static void main(String[] args) {

        Map<Player, Integer> points = new HashMap<>();


        // Создаем 10 игроков с очками 0
        for (int i = 1; i <= 10; i++) {
            points.put(new Player(i, "Player" + i, true), 0);
        }

        // Обновляем очки для конкретных игроков по id
        for (Player p : points.keySet()) {
            if (p.getId() == 4) points.put(p, 10);
            if (p.getId() == 7) points.put(p, 12);
            if (p.getId() == 8) points.put(p, 11);
            if (p.getId() == 9) points.put(p, 13);
            if (p.getId() == 10) points.put(p, 5);
        }

        // Находим топ-3 игроков по очкам
        Player top1 = null, top2 = null, top3 = null;
        int score1 = -1, score2 = -1, score3 = -1;

        for (Map.Entry<Player, Integer> entry : points.entrySet()) {
            int score = entry.getValue();
            Player p = entry.getKey();

            if (score > score1) {
                top3 = top2; score3 = score2;
                top2 = top1; score2 = score1;
                top1 = p; score1 = score;
            } else if (score > score2) {
                top3 = top2; score3 = score2;
                top2 = p; score2 = score;
            } else if (score > score3) {
                top3 = p; score3 = score;
            }
        }

        // Выводим топ-3 игроков
        System.out.println("Лучшие игроки:");
        if (top1 != null) System.out.println("1. " + top1 + " - Очки: " + score1);
        if (top2 != null) System.out.println("2. " + top2 + " - Очки: " + score2);
        if (top3 != null) System.out.println("3. " + top3 + " - Очки: " + score3);
    }
}