package tests;

import lesson9.Player;
import lesson9.PlayerService;
import lesson9.PlayerServiceJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Добавить игрока, есть json-файл")
public class PositivePlayerServiceYesJsonFileTest {
    private PlayerService playerService;
    private int existingPlayerId;

    @BeforeEach
    public void setup() {
        playerService = new PlayerServiceJSON();
        // Создаем одного игрока один раз и запоминаем его ID
        existingPlayerId = playerService.createPlayer("Sergio");
    }

    @Test
    @DisplayName("Добавить игрока")
    public void testAddPlayer() {
        String nickname = "Sergio";

        Player foundPlayer = null;
        for (Player p : playerService.getPlayers()) {
            if (p.getNick().equals(nickname)) {
                foundPlayer = p;
                break;
            }
        }
        assertNotNull(foundPlayer, "Игрок не найден");
        assertEquals(nickname, foundPlayer.getNick(), "Никнейм не совпадает");
    }

    @Test
    @DisplayName("Проверка, что записи из файла не потеряны")
    public void testLoadRecordsNotLost() {
        int id1 = playerService.createPlayer("Mary");
        int id2 = playerService.createPlayer("Tom");

        PlayerService loadedService = new PlayerServiceJSON();

        Collection<Player> players = loadedService.getPlayers();

        assertTrue(players.stream().anyMatch(p -> p.getId() == id1), "Не найдена запись для Mary");
        assertTrue(players.stream().anyMatch(p -> p.getId() == id2), "Не найдена запись для Tom");
    }

    @Test
    @DisplayName("Проверка целостности записей после загрузки")
    public void testRecordsNotBroken() {
        String nickname = "Gleb";

        int id = playerService.createPlayer(nickname);

        Player loadedPlayer = null;
        for (Player p : playerService.getPlayers()) {
            if (p.getNick().equals(nickname)) {
                loadedPlayer = p;
                break;
            }
        }
        assertNotNull(loadedPlayer, "Игрок с ником " + nickname + " не найден");
        assertEquals(nickname, loadedPlayer.getNick(), "Ник при загрузке поврежден или не совпадает");
    }

    @Test
    @DisplayName("Начислить баллы существующему игроку")
    public void addPointsToExistingPlayer() {
        int initialPoints = playerService.getPlayerById(existingPlayerId).getPoints();

        int pointsToAdd = 10;
        int totalPoints = playerService.addPoints(existingPlayerId, pointsToAdd);

        Player player = playerService.getPlayerById(existingPlayerId);
        assertEquals(initialPoints + pointsToAdd, totalPoints);
        assertEquals(initialPoints + pointsToAdd, player.getPoints());
    }

    @Test
    @DisplayName("Добавить очки поверх существующего")
    public void addPointsOverExisting() {
        int initialPoints = playerService.getPlayerById(existingPlayerId).getPoints();

        playerService.addPoints(existingPlayerId, 10);
        playerService.addPoints(existingPlayerId, 5); // добавляем еще очков

        Player player = playerService.getPlayerById(existingPlayerId);
        assertEquals(initialPoints + 15, player.getPoints());
    }
}