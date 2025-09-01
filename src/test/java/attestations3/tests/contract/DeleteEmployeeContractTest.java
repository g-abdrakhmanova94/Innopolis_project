package attestations3.tests.contract;

import attestations3.entities.EmployeeRequest;
import attestations3.entities.EmployeeResponse;
import attestations3.entities.ValidationErrorResponse;
import attestations3.helpers.EmployeeHelperDB;
import attestations3.helpers.HttpCodeRestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DeleteEmployeeContractTest {
    /**
     * Класс тестирования удаления сотрудников через API.
     * Методы включают:
     * - Проверку удаления ранее созданного сотрудника с ожидаемым статусом 200 и сообщением.
     * - Проверку удаления несуществующего сотрудника (ожидается статус 404).
     * - Проверку удаления с пустым ID (ожидается статус 404).
     * - Проверку удаления созданного сотрудника с проверкой заголовка (ожидается статус 200 и Content-Type в формате JSON).
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
        EmployeeRequest employee = new EmployeeRequest("Kazan", "Marina", "AQA", "Addington");
        createdEmployee = employeeHelperDB.postEmployee(employee);
    }

    @Test
    @DisplayName("Проверить удаление ранее созданного сотрудника")
    public void deleteEmployeeResponseTypes() {
        ValidationErrorResponse employee = given()
                .accept(ContentType.JSON)
                .when()
                .delete("/employee/" + createdEmployee.getId())
                .then().
                statusCode(HttpCodeRestAssured.OK).extract().body().as(ValidationErrorResponse.class); // Проверка ответа
        System.out.println("id" + " " + createdEmployee.getId());
        System.out.println("message" + " " + employee.getMessage());
    }

    @Test
    @DisplayName("Проверить удаление ранее несуществующего сотрудника")
    public void deleteEmployeeEmployeeNonExistentId() {
        int nonExistentId = 9999;
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/employee/" + nonExistentId)
                .then().
                statusCode(HttpCodeRestAssured.NOT_FOUND);
    }

    @Test
    @DisplayName("Проверить удаление с пустым id")
    public void deleteEmployeeEmptytId() {
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/employee/")
                .then().
                statusCode(HttpCodeRestAssured.NOT_FOUND);
    }

    @Test
    @DisplayName("Проверить удаление ранее созданного сотрудника, проверка заголовка")
    public void deleteEmployeeResponseHeaderTypes() {
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/employee/" + createdEmployee.getId())
                .then().
                statusCode(HttpCodeRestAssured.OK).header("Content-Type", "application/json");
    }
}