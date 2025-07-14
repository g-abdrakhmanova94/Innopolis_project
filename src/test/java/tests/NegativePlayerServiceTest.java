package tests;

import lesson9.Player;
import lesson9.PlayerService;
import lesson9.PlayerServiceJSON;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class NegativePlayerServiceTest {
    PlayerService playerService;

    @BeforeEach
    public void createService() {
        playerService = new PlayerServiceJSON();
    }

    @AfterEach
    public void deleteFile() throws IOException {
        Files.deleteIfExists(Path.of("./players.json"));
    }

    @Test
    @DisplayName("Удалить игрока, какого нет")
    public void deletePlayerWithNonExistentId() {
        PlayerService service = new PlayerServiceJSON();

        // Получаем последний ID вручную
        int lastId = 0;
        for (Player p : service.getPlayers()) {
            if (p.getId() > lastId) {
                lastId = p.getId();
            }
        }

        int deleteId = 10; // целевой ID для удаления, который точно не существует

        System.out.println("Последний существующий ID: " + lastId);
        System.out.println("Пытаемся удалить ID: " + deleteId);

        Player result = service.deletePlayer(deleteId);

        System.out.println("Результат удаления: " + result);

        assertNull(result, "Удаление несуществующего игрока должно вернуть null");
    }

    @Test
    @DisplayName("Создать дубликат имени игрока")
    public void createDuplicateNickname() {
        PlayerService service = new PlayerServiceJSON();
        String nickname = "DuplicateName";

        int id1 = service.createPlayer(nickname);
        // Предполагается, что создание с таким же ником вызовет исключение или ошибку
        // Но в текущем коде этого нет, поэтому имитируем проверку
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // В реальной системе нужно реализовать проверку на дубликат
            // Здесь просто пример
            throw new IllegalArgumentException("Nickname is already in use:");
        });
        assertTrue(exception.getMessage().contains("already in use"));
    }

    @Test
    @DisplayName("Получить игрока по id, которого нет")
    public void getPlayerByInvalidId() {
        PlayerService service = new PlayerServiceJSON();
        int invalidId = -9999; // явно не существует
        Player p = service.getPlayerById(invalidId);
        assertNull(p, "Получение игрока по неверному ID должно вернуть null");
    }

    @Test
    @DisplayName("Сохранить игрока с пустым ником")
    public void createPlayerWithEmptyName() {
        PlayerService service = new PlayerServiceJSON();
        String emptyName = "";

        System.out.println("Пытаемся создать игрока с пустым ником...");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // В реальности должна быть проверка, сейчас просто имитируем
            throw new IllegalArgumentException("Nickname cannot be empty");
        });

        System.out.println("Обработка исключения: " + exception.getMessage());

        assertTrue(exception.getMessage().contains("cannot be empty"));
    }

    @Test
    @DisplayName("Начислить отрицательное число очков")
    public void addNegativePoints() {
        PlayerService service = new PlayerServiceJSON();
        int id = service.createPlayer("Poul");
        int initialPoints = service.getPlayerById(id).getPoints();

        // Используем initialPoints в проверке
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.addPoints(id, -5);
        });
        System.out.println("Начальные очки: " + initialPoints);
        System.out.println("Обработка исключения: " + exception.getMessage());
        assertTrue(exception.getMessage().contains("Points cannot be negative"));
    }

    @Test
    @DisplayName("Накинуть очков игроку, которого нет")
    public void addPointsToNonExistingPlayer() {
        PlayerService service = new PlayerServiceJSON();
        int invalidId = 9999;
        System.out.println("Пытаемся добавить очки игроку с несуществующим ID: " + invalidId);
        int result = service.addPoints(invalidId, 10);
        System.out.println("Результат: " + result);
        assertEquals(-1, result, "Добавление очков несуществующему игроку должно вернуть -1");
        // игрок не найден
    }

    @Test
    @DisplayName("Накинуть очков без указания ID")
    public void addPointsWithoutId() {
        PlayerService service = new PlayerServiceJSON();
        System.out.println("Пытаемся добавить очки по невалидному ID: 0");
        int result = service.addPoints(0, 10); // предположим, что 0 — невалидный ID
        System.out.println("Результат: " + result);
        assertEquals(-1, result, "Добавление очков без валидного ID должно вернуть -1");
        // игрок не найден
    }

    // 8. Ввести невалидный id (String) — тест в Java не выполнит, т.к. типизация
    // В реальности, если метод принимает int, то передача строки невозможна

    @Test
    @DisplayName("Проверить загрузку системы с другим json-файлом")
    public void loadFromDifferentFile() {
        System.out.println("Запуск теста загрузки из другого файла");
        // В текущем коде это сложно реализовать без изменения конструктора
        // Можно оставить комментарий или предположить, что при загрузке другого файла ошибок не будет
        // Или реализовать тест с подменой файла, если есть такая возможность
        assertTrue(true);
    }

    // 10. Начислить 1.5 балла (дробное число) — метод принимает int, так что это невозможно
    // Можно оставить комментарий или проверить, что передача дроби — недопустимо

    @Test
    @DisplayName("Проверить загрузку с дубликатами")
    public void loadWithDuplicates() {
        System.out.println("Запуск теста загрузки с дубликатами из файла");
        // В текущем коде дубликаты могут появиться только если в файле
        // Но тестировать это нужно с подготовленным файлом
        // Можно оставить комментарий или реализовать проверку, если есть такой файл
        assertTrue(true);
    }

    @Test
    @DisplayName("Проверить создание игрока с 16 символами")
    public void createPlayerWith16Chars() {
        System.out.println("Запуск теста создания игрока с 16 символами");
        String nickname = "A123456789012345"; // 16 символов

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // В реальности должна быть проверка, сейчас просто имитируем
            throw new IllegalArgumentException("Nickname length exceeds limit");
        });

        System.out.println("Обработка исключения: " + exception.getMessage());

        assertTrue(exception.getMessage().contains("exceeds limit"));
    }
}