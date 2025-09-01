package attestations3.entities;

public class UserRestAssured {
    /**
     * Класс для представления пользователя в запросах RestAssured.
     * Поля:
     * - password: пароль пользователя.
     * - username: имя пользователя.
     * Методы:
     * - Конструктор для инициализации полей.
     * - Геттеры и сеттеры для доступа к полям.
     */


    private String password;
    private String username;

    public UserRestAssured(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
