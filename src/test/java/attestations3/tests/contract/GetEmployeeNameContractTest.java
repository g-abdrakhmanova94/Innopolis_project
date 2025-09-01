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

public class GetEmployeeNameContractTest {
    /**
     * Класс тестирования получения сотрудников по имени через API.
     * Методы включают:
     * - Проверку типов параметров в ответе для существующего сотрудника.
     * - Проверку статуса ответа при запросе сотрудника с существующим именем (ожидается статус 200).
     * - Проверку статуса ответа для несуществующего имени (ожидается статус 404).
     * - Проверку статуса ответа при использовании специальных символов вместо имени (ожидается статус 404).
     * - Проверку статуса ответа и заголовка для существующего сотрудника.
     * - Проверку ответа на неверный адрес (ожидается статус 404).
     * - Проверку статуса ответа при пустом имени (ожидается статус 404).
     */
    private static EmployeeHelperDB employeeHelperDB;

    private EmployeeResponse createdEmployee;

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
    @DisplayName("Проверить типы параметров в ответе при получении сотрудника по имени")
    public void getEmployeeResponseTypes() {
        given()
                .when()
                .get("/employee/name/" + createdEmployee.getName()) // Используем имя для запроса
                .then()
                .statusCode(HttpCodeRestAssured.OK)
                .extract().as(EmployeeResponse.class); // Проверяем, что ответ соответствует типу EmployeeResponse
    }

    @Test
    @DisplayName("Проверить статус код ответа сотрудника с существующим именем")
    public void getEmployeeByValidName() {
        given()
                .when()
                .get("/employee/name/" + createdEmployee.getName()) // Используем имя для запроса
                .then()
                .statusCode(HttpCodeRestAssured.OK);
    }

    @Test
    @DisplayName("Проверить статус код ответа сотрудника с несуществующим именем")
    public void getEmployeeByInvalidName() {
        String invalidName = "Nik";
        given()
                .when()
                .get("/employee/name/" + invalidName)
                .then()
                .statusCode(HttpCodeRestAssured.NOT_FOUND);
    }

    @Test
    @DisplayName("Проверить статус код ответа сотрудника со спецсимволами вместо имени")
    public void getEmployeeBySpecialSymbolsName() {
        String SpecialSymbolsName = "%$#^&";
        given()
                .when()
                .get("/employee/name/" + SpecialSymbolsName)
                .then()
                .statusCode(HttpCodeRestAssured.NOT_FOUND);
    }

    @Test
    @DisplayName("Проверить статус код ответа и заголовок")
    public void getEmployeeWithHeader() {
        given()
                .when()
                .get("/employee/name/" + createdEmployee.getName())
                .then()
                .statusCode(HttpCodeRestAssured.OK)
                .header("Content-Type", "application/json");
    }

    @Test
    @DisplayName("Отправка запроса на неверный адрес")
    public void getEmployeeInvalidUrl() {
        given()
                .when()
                .get("/user/" + createdEmployee.getName())
                .then()
                .statusCode(HttpCodeRestAssured.NOT_FOUND)
                .header("Content-Type", "text/html; charset=utf-8");
    }

    @Test
    @DisplayName("Проверить сотрудника с пустым именем")
    public void getEmployeeWithEmptyName() {
        given()
                .when()
                .get("/employee/name/")
                .then()
                .statusCode(HttpCodeRestAssured.NOT_FOUND)
                .header("Content-Type", "text/html; charset=utf-8");
    }
}
