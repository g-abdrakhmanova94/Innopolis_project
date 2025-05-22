package net.lesson7;

import java.util.HashSet;
import java.util.Set;

public class HashSetPlayers {

    public static void main(String[] args) {

        Set<String> players= new HashSet<>(); // Создаем хранилище игроков

        // Создаем 10 игроков
        Player p1 = new Player(1, "Jacob Williams", true);
        Player p2 = new Player(2, "Rick Bell", true);
        Player p3 = new Player(3, "Anderson Torres", true);
        Player p4 = new Player(4, "Mason Cooper", true);
        Player p5 = new Player(5, "Michael Harris", true);
        Player p6 = new Player(6, "Andrew Ross", true);
        Player p7 = new Player(7, "Tyler Evans", true);
        Player p8 = new Player(8, "Norman Diaz", true);
        Player p9 = new Player(9, "Simon Nelson", true);
        Player p10 = new Player(10, "Eric Hill", true);

        // Добавляем игроков в хранилище
        players.add("p1");
        players.add("p2");
        players.add("p3");
        players.add("p4");
        players.add("p5");
        players.add("p6");
        players.add("p7");
        players.add("p8");
        players.add("p9");
        players.add("p10");


        // Попытка добавить дублирующего игрока
        players.add("p1"); // Повторное добавление первого игрока

        // Выводим количество уникальных игроков
        System.out.println("Общее количество игроков: " + players.size());

        }
    }
