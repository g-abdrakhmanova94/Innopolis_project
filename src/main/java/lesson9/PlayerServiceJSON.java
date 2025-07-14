package lesson9;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PlayerServiceJSON implements PlayerService {
    private String fileName = "players.json";
    private List<Player> players = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();
    private int nextId = 1;

    public PlayerServiceJSON() {
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                players = mapper.readValue(file, new TypeReference<List<Player>>() {});
                // Обновляем nextId так, чтобы он был больше максимального id
                int maxId = 0;
                for (Player p : players) {
                    if (p.getId() > maxId) {
                        maxId = p.getId();
                    }
                }
                nextId = maxId + 1;
            } else {
                players = new ArrayList<>();
                nextId = 1; // начинаем с 1, если файла нет
            }
        } catch (IOException e) {
            e.printStackTrace();
            players = new ArrayList<>();
            nextId = 1; // при ошибке тоже начинаем с 1
        }
    }

    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player getPlayerById(int id) {
        for (Player p : players) {
            if (p.getId() == id) {
                System.out.println("Найден игрок по ID " + id + ": " + p);
                return p;
            }
        }
        System.out.println("Игрок с ID " + id + " не найден");
        return null;
    }

    @Override
    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public int createPlayer(String nickname) {
        Player p = new Player(nextId++, nickname, 0, false);
        players.add(p);
        saveToFile();
        System.out.println("Создан игрок: " + p);
        return p.getId();
    }

    @Override
    public Player deletePlayer(int id) {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getId() == id) {
                iterator.remove();
                saveToFile();
                return p;
            }
        }
        return null;
    }

    @Override
    public int addPoints(int playerId, int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        for (Player p : players) {
            if (p.getId() == playerId) {
                p.setPoints(p.getPoints() + points);
                saveToFile();
                return p.getPoints();
            }
        }
        return -1;
    }
}