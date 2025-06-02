package net.lesson9;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;

@XmlRootElement(name = "players")
public class PlayerServiceXML implements PlayerService {

    @XmlElement(name = "player")
    private List<Player> players = new ArrayList<>();

    private int nextId = 1;
    private String fileName = "players.xml";

    // Публичный конструктор
    public PlayerServiceXML() {
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                JAXBContext context = JAXBContext.newInstance(PlayerServiceXML.class);
                Unmarshaller um = context.createUnmarshaller();
                PlayerServiceXML loaded = (PlayerServiceXML) um.unmarshal(file);
                this.players = loaded.players;

                int maxId = 0;
                for (Player p : players) {
                    if (p.getId() > maxId) maxId = p.getId();
                }
                this.nextId = maxId + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(PlayerServiceXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(this, new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player getPlayerById(int id) {
        for (Player p : players) if (p.getId() == id) return p;
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
        return p.getId();
    }

    @Override
    public Player deletePlayer(int id) {
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player p = it.next();
            if (p.getId() == id) {
                it.remove();
                saveToFile();
                return p;
            }
        }
        return null;
    }

    @Override
    public int addPoints(int playerId, int points) {
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