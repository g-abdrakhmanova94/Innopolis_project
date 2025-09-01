package attestations3.tests.contract;

import attestations3.entities.EmployeeRequest;
import attestations3.helpers.AuthHelper;
import attestations3.helpers.HttpCodeRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PostEmployeeContractTest {
    /**
     * Класс тестирования создания сотрудников через API.
     * Методы включают:
     * - Создание сотрудника с заполненными всеми полями (ожидание успешного статуса 201).
     * - Проверку обработки обязательных полей и неуспеха при неверных данных (статусы 400, 401, 404).
     * - Тестирование ввода данных с неправильным форматом и типом (например, слишком длинные значения, только спецсимволы).
     * - Обработку ситуаций, когда запрос отправляется без авторизации или на неправильный адрес.
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
    @DisplayName("Создание сотрудника, заполнены все поля")
    public void postEmployeeCode200() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.CREATED);
    }

    @Disabled("Баг - не удается создать юзера с обяз. полями, см.п.1 файла BAGS.MD")
    @Test
    @DisplayName("Создание сотрудника только с обяз. полями")
    public void postEmployeeRequiredFieldsCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.CREATED);
    }

    @Test
    @DisplayName("Создание сотрудника без обязательных полей")
    public void postEmployeeCode400THIRDVARIANT() {
        String token = authHelper.getToken("Lala", "Ron");

        ExtractableResponse<Response> response = given()
                .body(new EmployeeRequest("Kazan"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST).extract();

        List<String> misFields = response.jsonPath().getList("missing_fields");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(misFields.get(0)).isEqualTo("name");
            softAssertions.assertThat(misFields.get(1)).isEqualTo("surname");
            softAssertions.assertThat(misFields.get(2)).isEqualTo("position");
        });
    }

    @Test
    @DisplayName("Создание сотрудника без авторизации")
    public void postEmployeeCode401() {
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.UNAUTHORIZED);
    }

    @Disabled("Баг - не тот статус код при создании юзера со слишком длинным значением в поле, см.п.2 файла BAGS.MD")
    @Test
    @DisplayName("Создание сотрудника со слишком длинным значением в поле")
    public void postEmployeeEmployeeWithTooLongValuesCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "stringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstring", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание сотрудника, переданы несуществующие данные для получения токена")
    public void postEmployeeWithInvalidTokenCode401() {
        String token = authHelper.getToken("La", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.UNAUTHORIZED);
    }

    @Disabled("Баг - удается создать юзера с одной буквой в поле, см.п.3 файла BAGS.MD")
    @Test
    @DisplayName("Создание сотрудника, в имени одна буква")
    public void postEmployeeCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "M", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание сотрудника, значения в полях отсутствуют")
    public void postEmployeeEmptyFieldsCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("", "", "", ""))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание сотрудника, в заголовке указан не тот тип данных")
    public void postEmployeeCode415() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.XML)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.UNSUPPORTED_MEDIA_TYPE);
    }

    @Disabled("Баг - удается создать юзера только со спецсимволами в полях, см.п.4 файла BAGS.MD")
    @Test
    @DisplayName("Создание сотрудника, заполнено поле только спецсимволами")
    public void postEmployeeInvalidCode400() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "@$%$№*/", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/employee").
                then().
                statusCode(HttpCodeRestAssured.BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание сотрудника, запрос отправлен не на тот адрес")
    public void postEmployeeCode404() {
        String token = authHelper.getToken("Lala", "Ron");
        given()
                .body(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"))
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post("/user").
                then().
                statusCode(HttpCodeRestAssured.NOT_FOUND);
    }
}
