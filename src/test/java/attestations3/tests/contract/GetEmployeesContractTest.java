package attestations3.tests.contract;

import attestations3.helpers.HttpCodeRestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetEmployeesContractTest {
    /**
     * Класс тестирования получения списка сотрудников через API.
     * Методы включают:
     * - Проверку кода ответа (ожидается статус 200) при получении списка сотрудников.
     * - Проверку структуры тела ответа, включая наличие ключей в объекте сотрудника.
     * - Проверку уникальности идентификаторов сотрудников в ответе.
     * - Проверку обработки запроса на несуществующий адрес (ожидается статус 404).
     */

    @Test
    @DisplayName("Проверить код ответа при получении списка сотрудников")
    public void getEmployeesCode200() {
        given().
                baseUri("https://innopolispython.onrender.com").
                when().
                get("/employees").
                then().
                statusCode(HttpCodeRestAssured.OK).header("Content-Type", "application/json");
    }

    @Test
    @DisplayName("Проверить структуру тела ответа")
    public void getEmployeesResponseBodyCode200() {
        Response response = given()
                .baseUri("https://innopolispython.onrender.com")
                .when()
                .get("/employees")
                .then()
                .statusCode(HttpCodeRestAssured.OK)
                .header("Content-Type", "application/json")
                .extract().response(); // Извлечь ответ

        // Получить динамический индекс
        int randomIndex = new Random().nextInt(response.jsonPath().getList("$").size());

        // Проверить структуру ответа в выбранной записи
        Map<String, Object> employee = response.jsonPath().getMap("[" + randomIndex + "]");

        assertThat(employee, hasKey("city"));
        assertThat(employee, hasKey("id"));
        assertThat(employee, hasKey("name"));
        assertThat(employee, hasKey("position"));
        assertThat(employee, hasKey("surname"));
    }

    @Test
    @DisplayName("Проверить, что все id в ответе уникальные")
    public void getEmployeesUniqueIdCode200() {
        Response response = given()
                .baseUri("https://innopolispython.onrender.com")
                .when()
                .get("/employees")
                .then()
                .statusCode(HttpCodeRestAssured.OK) // Проверка статуса
                .header("Content-Type", "application/json") // Проверка заголовка
                .extract().response(); // Извлечение ответа

        // Извлечение списка id сотрудников
        List<Integer> ids = response.jsonPath().getList("id", Integer.class);

        Set<Integer> uniqueIds = new HashSet<>(ids);
        // Проверка, что все id уникальные
        assertThat(uniqueIds.size(), is(equalTo(ids.size())));
    }

    @Test
    @DisplayName("Запрос на несуществующий адрес")
    public void getEmployeesCode404() {
        given().
                baseUri("https://innopolispython.onrender.com").
                when().
                get("/users").
                then().
                statusCode(HttpCodeRestAssured.NOT_FOUND).header("Content-Type", "text/html; charset=utf-8");
    }
}
