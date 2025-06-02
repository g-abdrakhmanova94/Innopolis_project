package net.lesson9;

import jakarta.xml.bind.JAXBException;

public class TestClassXML {
    public static void main(String[] args) throws JAXBException {
        PlayerService service = new PlayerServiceXML();

        // Создаем нового игрока
        int id = service.createPlayer("Roman");
        System.out.println("Создан игрок с id: " + id);

        // Добавляем очки
        int newPoints = service.addPoints(id, 50);
        System.out.println("Обновленный счет у игрока: " + newPoints);


        // Получаем всех игроков
        System.out.println("Все игроки:");
        for (Player p : service.getPlayers()) {
            System.out.println(p);
        }

        // Удаляем игрока
        Player removed = service.deletePlayer(id);
        System.out.println("Удален игрок: " + removed);

        // Проверка, что удаление прошло успешно
        System.out.println("Игроки после удаления:");
        for (Player p : service.getPlayers()) {
            System.out.println(p);
        }
    }
}



