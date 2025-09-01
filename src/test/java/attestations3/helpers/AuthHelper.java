package attestations3.helpers;

import attestations3.entities.UserRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthHelper {
    /**
     * Класс для работы с аутентификацией пользователей.
     * Методы включают:
     * - Конструктор для установки базового URI.
     * - getToken: получает токен аутентификации по имени пользователя и паролю.
     */
    public AuthHelper() {
        RestAssured.baseURI = "https://innopolispython.onrender.com";
    }

    public String getToken(String password, String username) {
        return given().
                body(new UserRestAssured(password, username)).contentType(ContentType.JSON).
                when().
                post("/login").jsonPath().getString("token");
    }
}
