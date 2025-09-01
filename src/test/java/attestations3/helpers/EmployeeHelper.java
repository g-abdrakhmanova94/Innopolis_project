package attestations3.helpers;

import attestations3.entities.EmployeeRequest;
import attestations3.entities.EmployeeResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class EmployeeHelper {
    /**
     * Класс для работы с API сотрудников.
     * Методы включают:
     * - Конструктор для инициализации AuthHelper и установки базового URI.
     * - postEmployee: отправляет запрос на создание сотрудника и возвращает его ID.
     * - getEmployee: получает данные сотрудника по ID и возвращает объект EmployeeResponse.
     * - deleteEmployee: удаляет сотрудника по ID через HTTP запрос.
     */
    private final AuthHelper authHelper;

    public EmployeeHelper() {
        authHelper = new AuthHelper();
        RestAssured.baseURI = "https://innopolispython.onrender.com";
    }

    public int postEmployee(EmployeeRequest employee) {
        String token = authHelper.getToken("Lala", "Ron");

        JsonPath jsonPath = given()
                .body(employee)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").jsonPath();
        try {
            return jsonPath.getInt("id");
        } catch (NullPointerException nullPointerException) {
            return -1;
        }
    }

    public EmployeeResponse getEmployee(int id) {
        Response response = given().
                when().
                get("/employee/" + id);
        try {
            return response.as(EmployeeResponse.class);
        } catch (IllegalStateException exception) {
            return new EmployeeResponse();
        }

    }

    public void deleteEmployee(int id) {
        given().
                when().
                delete("/employee/" + id);
    }
}
