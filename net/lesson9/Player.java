package net.lesson9;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {
    @XmlElement
    private int id;
    @XmlElement
    private String nick;
    @XmlElement
    private int points;
    @XmlElement(name = "online")
    private boolean isOnline;

    public Player() {}

    public Player(int id, String nick, int points, boolean isOnline) {
        this.id = id;
        this.nick = nick;
        this.points = points;
        this.isOnline = isOnline;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNick() { return nick; }
    public void setNick(String nick) { this.nick = nick; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public boolean isOnline() { return isOnline; }
    public void setOnline(boolean online) { this.isOnline = online; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getId() == player.getId() &&
                getPoints() == player.getPoints() &&
                isOnline() == player.isOnline() &&
                Objects.equals(getNick(), player.getNick());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNick(), getPoints(), isOnline());
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", points=" + points +
                ", isOnline=" + isOnline +
                '}';
    }
}