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
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Добавить игрока, нет json-файла")
public class PositivePlayerServiceNotJsonFileTest {
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
    @DisplayName("Проверить наличие игрока в списке и запрос по нему инфо")
    public void testAddPlayer() {
        String nickname = "Nicky";

        int id = playerService.createPlayer(nickname); // добавляем игрока

        Player player = playerService.getPlayerById(id); // получаем по id

        // Проверка, что игрок не null и ник совпадает
        assertNotNull(player, "Игрок не найден");
        assertEquals(nickname, player.getNick(), "Никнейм не совпадает");
        assertEquals(id, player.getId(), "ID должен совпадать");

    }

    @Test
    @DisplayName("Получить список игроков")
    public void getPlayersTest() {
        PlayerService service = new PlayerServiceJSON();

        // Создаем двух игроков
        int id1 = service.createPlayer("Alice");
        int id2 = service.createPlayer("Bob");

        // Получаем список игроков
        var players = service.getPlayers();

        // Проверяем, что список не пустой
        assertFalse(players.isEmpty());

        // Проверяем, что список содержит игроков с нужными ID
        assertTrue(players.stream().anyMatch(p -> p.getId() == id1 && p.getNick().equals("Alice")));
        assertTrue(players.stream().anyMatch(p -> p.getId() == id2 && p.getNick().equals("Bob")));
    }

    @Test
    @DisplayName("Удалить игрока и проверить его отсутствие в списке")
    public void deletePlayerTest() {
        int playerId = playerService.createPlayer("Helen");
        playerService.deletePlayer(playerId);
        Player player = playerService.getPlayerById(playerId);
        assertNull(player);
    }

    @Test
    @DisplayName("Проверить, что id пользователя всегда уникальный")
    public void testUniqueIdsAfterCreateDeleteAdd() {
        // Создаем 5 игроков
        int[] ids = new int[5];
        for (int i = 0; i < 5; i++) {
            ids[i] = playerService.createPlayer("Player" + (i + 1));
        }

        // Удаляем 3-го игрока
        playerService.deletePlayer(ids[2]);

        // Создаем еще одного игрока
        int newId = playerService.createPlayer("NewPlayer");

        // Проверяем, что ID нового игрока == 6 (если первые 5 были 1-5, а 3-й удален)
        assertEquals(6, newId, "ID нового игрока должно быть 6");

        // Проверяем, что все остальные ID уникальны
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                assertNotEquals(ids[i], ids[j], "ID должны быть уникальными");
            }
        }
    }
        @Test
        @DisplayName("Проверить создание игрока с 15 символами")
        public void CreatePlayerWith15Name () {
            String nickname = "A12345678901234"; // 15 символов
            int playerId = playerService.createPlayer(nickname);
            Player player = playerService.getPlayerById(playerId);
            assertNotNull(player, "Игрок должен быть создан");
            assertEquals(nickname, player.getNick(), "Никнейм должен совпадать");
            assertEquals(15, player.getNick().length(), "Длина никнейма должна быть 15 символов");
        }
    }
