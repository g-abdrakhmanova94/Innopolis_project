package lesson9;

public class TestClass {

    public static void main(String[] args) {
        PlayerService service = new PlayerServiceJSON();

        // Создаем нового игрока
        int playerId = service.createPlayer("Bison");
        System.out.println("Создан игрок с id: " + playerId);

        // Добавляем очки
        int newPoints = service.addPoints(playerId, 100);
        System.out.println("Обновленный счет у игрока: " + newPoints);

        // Получаем всех игроков
        System.out.println("Все игроки:");
        for (Player p : service.getPlayers()) {
            System.out.println(p);
        }

        // Удаляем игрока
        Player removed = service.deletePlayer(playerId);
        System.out.println("Удален игрок: " + removed);

        // Проверка, что удаление прошло успешно
        System.out.println("Игроки после удаления:");
        for (Player p : service.getPlayers()) {
            System.out.println(p);
        }
    }
}






