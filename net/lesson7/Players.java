package net.lesson7;

public class Players {
    public static void main(String[] args) {
      Player player1 = new Player(1, "David Check", true);
      Player player2 = new Player(1,"David Check", true );

        // Проверка равенства по ссылке
        System.out.println("player1 == player2: " + (player1 == player2)); // false

        // Проверка равенства по содержанию
        System.out.println("player1.equals(player2): " + player1.equals(player2)); // true

    }
}
