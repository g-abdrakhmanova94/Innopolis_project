package attestations3.tests.contract;

import attestations3.entities.UserRestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;

public class LoginTest {
    /**
     * Класс тестирования авторизации пользователей через API.
     * Метод включает:
     * - Проверку успешной авторизации с использованием корректных учетных данных (ожидается статус 200 и не пустой токен в ответе).
     * Тест использует UserRestAssured для передачи данных пользователя.
     */

    @BeforeAll
    public static void setUp() {
        baseURI = "https://innopolispython.onrender.com";
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void validLogin() {
        given().
                body(new UserRestAssured("Lala", "Ron")).contentType(ContentType.JSON).
                when().
                post("/login").
                then()
                .statusCode(200).body("token", is(not(blankString())));
    }
}


