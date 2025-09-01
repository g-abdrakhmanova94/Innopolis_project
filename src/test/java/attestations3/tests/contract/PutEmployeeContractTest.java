package attestations3.tests.contract;

import attestations3.entities.EmployeeRequest;
import attestations3.entities.ValidationErrorResponse;
import attestations3.helpers.AuthHelper;
import attestations3.helpers.HttpCodeRestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;


import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PutEmployeeContractTest {
    /**
     * Класс тестирования обновления данных сотрудников через API.
     * Методы включают:
     * - Полное и частичное обновление данных существующего сотрудника с проверкой кода ответа и тела.
     * - Обработку статуса 401 при неверной авторизации.
     * - Проверку обработки 404 для несуществующего сотрудника или неправильного URL.
     * - Проверку обновления с пустыми значениями и обязательными полями.
     * - Обработку 400 для неправильно введённых данных (например, спецсимволы или слишком длинные значения).
     * Тесты используют AuthHelper для получения токена авторизации.
     */
    private AuthHelper authHelper;

    @BeforeAll
    public static void setUri() {
        baseURI = "https://innopolispython.onrender.com";
    }

    @BeforeEach
    public void setUp() {
        authHelper = new AuthHelper();
    }

    @Test
    @DisplayName("Полное обновление данных существующего сотрудника, проверка статус код ответа и тело")
    public void putEmployeeFullCode200() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/employee/" + "87").
                then().
                statusCode(HttpCodeRestAssured.OK).header("Content-Type", "application/json")
                .body("id", equalTo(87))
                .body("message", equalTo("Employee updated successfully"));

    }

    @Test
    @DisplayName("Частичное обновление данных существующего сотрудника, проверка статус код ответа и тело")
    public void putEmployeeCode200() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Oleg", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/employee/" + "87").
                then().
                statusCode(HttpCodeRestAssured.OK).header("Content-Type", "application/json")
                .body("id", equalTo(87))
                .body("message", equalTo("Employee updated successfully"));

    }

    @Test
    @DisplayName("Обновление данных существующего сотрудника, переданы несуществующие данные для получения токена")
    public void putEmployeeCode401() {
        String token = authHelper.getToken("La", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/employee/" + "8").
                then().
                statusCode(HttpCodeRestAssured.UNAUTHORIZED).header("Content-Type", "application/json");

    }

    @Test
    @DisplayName("Обновление данных несуществующего сотрудника")
    public void putEmployeeCheckBody() {
        String token = authHelper.getToken("Lala", "Ron");

        ValidationErrorResponse employee = given()
                .body(new EmployeeRequest("Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/employee/" + "25").
                then().
                statusCode(HttpCodeRestAssured.NOT_FOUND).extract().body().as(ValidationErrorResponse.class);

        System.out.println(employee.getError());
    }

    @Test
    @DisplayName("Обновление данных существующего сотрудника пустым значением необязательного поля")
    public void putEmployeeOptionalFieldCode200() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/employee/" + "8").
                then().
                statusCode(HttpCodeRestAssured.OK).header("Content-Type", "application/json")
                .body("id", equalTo(8))
                .body("message", equalTo("Employee updated successfully"));

    }

    @Test
    @DisplayName("Обновление данных существующего сотрудника пустыми значениями обязательных полей")
    public void putEmployeeRequiredFieldsCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        ValidationErrorResponse employee = given()
                .body(new EmployeeRequest("Kazan", "", "", ""))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when().
                put("/employee/" + "2")
                .then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST).extract().body().as(ValidationErrorResponse.class);

        System.out.println(employee.getError());
        System.out.println(employee.getEmptyFields());
    }

    @Test
    @DisplayName("Отправка запроса на несуществующий адрес")
    public void putEmployeeCode404() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/user/" + "8").
                then().
                statusCode(HttpCodeRestAssured.NOT_FOUND).header("Content-Type", "text/html; charset=utf-8");

    }

    @Test
    @DisplayName("Обновление сотрудника без авторизации")
    public void putEmployeeInvalidAuthCode401() {
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON).
                when().
                put("/employee/" + "8").
                then().
                statusCode(HttpCodeRestAssured.UNAUTHORIZED).header("Content-Type", "application/json");

    }

    @Disabled("Баг - удается обновить данные юзера только со спецсимволами в поле, см.п.5 файла BAGS.MD")
    @Test
    @DisplayName("Частичное обновление данных существующего сотрудника, ввод только спецсимволов в поле")
    public void putEmployeeInvalidCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "&&^%%", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/employee/" + "87").
                then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST).header("Content-Type", "application/json");

    }

    @Disabled("Баг - удается обновить данные юзера одной буквой в поле, см.п.6 файла BAGS.MD")
    @Test
    @DisplayName("Частичное обновление данных существующего сотрудника, ввод только одной буквы в поле")
    public void putEmployeeOneLetterCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Ben", "AQA", "A"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/employee/" + "87").
                then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST).header("Content-Type", "application/json");

    }

    @Disabled("Баг - не тот статус код при обновлении юзера со слишком длинным значением в поле, см.п.7 файла BAGS.MD")
    @Test
    @DisplayName("Частичное обновление данных существующего сотрудника со слишком длинным значением в поле")
    public void putEmployeeWithTooLongValuesCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Asaaaaaaaaaaaaaaaalsakmaslkmasklsamkaslmsdlkmsdldskmldkmdldssdasdsddsdsdsdsdsdskmsdlkdsdsdsddssddsdsdssdlmslksdnlds"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                put("/employee/" + "87").
                then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST).header("Content-Type", "application/json");

    }
}
