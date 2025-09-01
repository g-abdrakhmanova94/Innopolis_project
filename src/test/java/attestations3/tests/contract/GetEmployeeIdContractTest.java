package attestations3.tests.contract;

import attestations3.entities.EmployeeRequest;
import attestations3.entities.EmployeeResponse;
import attestations3.helpers.EmployeeHelperDB;
import attestations3.helpers.HttpCodeRestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GetEmployeeIdContractTest {
    /**
     * Класс тестирования получения сотрудников по ID через API.
     * Методы включают:
     * - Проверку типов параметров в ответе для сотрудника с существующим ID.
     * - Проверку статуса ответа при запросе сотрудника с действительным ID (ожидается статус 200).
     * - Проверку статуса ответа для несуществующего ID (ожидается статус 404).
     * - Проверку статуса ответа при использовании отрицательного ID (ожидается статус 404).
     * - Проверку статуса ответа и заголовка для существующего сотрудника.
     * - Проверку ответа на неверный адрес (ожидается статус 404).
     * - Проверку статуса ответа при пустом ID (ожидается статус 404).
     */
    private static EmployeeHelperDB employeeHelperDB;

    private EmployeeResponse createdEmployee; // Изменено на EmployeeResponse

    @BeforeAll
    public static void setUri() throws SQLException {
        employeeHelperDB = new EmployeeHelperDB();
        baseURI = "https://innopolispython.onrender.com";
    }

    @BeforeEach
    public void setUp() throws SQLException {
        EmployeeRequest employee = new EmployeeRequest("Kazan", "Mary", "AQA", "Addington");
        createdEmployee = employeeHelperDB.postEmployee(employee);
    }

    @Test
    @DisplayName("Проверить типы параметров в ответе при получении сотрудника по ID")
    public void getEmployeeResponseTypes() {
        given()
                .when()
                .get("/employee/" + createdEmployee.getId()) // Используем ID для запроса
                .as(EmployeeResponse.class);
    }

    @Test
    @DisplayName("Проверить статус код ответа сотрудника с существующим id")
    public void getEmployeeByValidId() {
        given()
                .when()
                .get("/employee/" + createdEmployee.getId()) // Используем ID для запроса
                .then()
                .statusCode(HttpCodeRestAssured.OK);
    }

    @Test
    @DisplayName("Проверить статус код ответа сотрудника с несуществующим id")
    public void getEmployeeByInvalidId() {
        int invalidId = 9999;
        given()
                .when()
                .get("/employee/" + invalidId)
                .then()
                .statusCode(HttpCodeRestAssured.NOT_FOUND);
    }

    @Test
    @DisplayName("Проверить статус код ответа сотрудника с отрицательным id")
    public void getEmployeeByNegativeId() {
        int negativeId = -2;
        given()
                .when()
                .get("/employee/" + negativeId)
                .then()
                .statusCode(HttpCodeRestAssured.NOT_FOUND);
    }

    @Test
    @DisplayName("Проверить статус код ответа и заголовок")
    public void getEmployeeWithHeader() {
        given()
                .when()
                .get("/employee/" + createdEmployee.getId()) // Используем ID для запроса
                .then()
                .statusCode(HttpCodeRestAssured.OK)
                .header("Content-Type", "application/json");
    }

    @Test
    @DisplayName("Отправка запроса на неверный адрес")
    public void getEmployeeInvalidUrl() {
        given()
                .when()
                .get("/user/" + createdEmployee.getId()) // Используем ID для запроса
                .then()
                .statusCode(HttpCodeRestAssured.NOT_FOUND)
                .header("Content-Type", "text/html; charset=utf-8");
    }

    @Test
    @DisplayName("Проверить сотрудника с пустым id")
    public void getEmployeeWithEmptyId() {
        given()
                .when()
                .get("/employee/")
                .then()
                .statusCode(HttpCodeRestAssured.NOT_FOUND)
                .header("Content-Type", "text/html; charset=utf-8");
    }
}