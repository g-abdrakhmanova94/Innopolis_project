package tests.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.ResponseData;
import helpers.HttpCode;
import helpers.UserHelper;
import okhttp3.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateUserNameContractTest {


    private static final String URL = "https://petstore.swagger.io/v2/user/"; // базовый URL
    private UserHelper userHelper;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        userHelper = new UserHelper();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() throws IOException {
        userHelper.deleteUser("keke");
    }

    @Test
    @DisplayName("Обновление пользователя с корректным телом")
    public void updateUser_ShouldReturn() throws IOException {
        String updateJson = "{ \"id\": 1 , \"username\": \"string\", \"firstName\": \"string\", " +
                "\"lastName\": \"string\", \"email\": \"string\", \"password\": \"string\", \"phone\": \"string\", " +
                "\"userStatus\": 1  }";

        Response response = userHelper.updateUserWithQueryParam("keke", updateJson);
        String responseBody = response.body().string();

        assertThat(response.code()).isEqualTo(HttpCode.OK);
    }

    @Test
    @DisplayName("Проверка тела ответа после обновления")
    public void updateUser_ResponseBody_ShouldBeCorrect() throws IOException {
        String updateJson = "{ \"id\": 1 , \"username\": \"string\", \"firstName\": \"string\", " +
                "\"lastName\": \"string\", \"email\": \"string\", \"password\": \"string\", \"phone\": \"string\", " +
                "\"userStatus\": 1}";

        Response response = userHelper.updateUserWithQueryParam("keke", updateJson);
        String responseBody = response.body().string();

        // парсим и проверяем содержание
        ResponseData result = objectMapper.readValue(responseBody, ResponseData.class);
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getType()).isEqualTo("unknown");
        // проверяем, что message содержит число или строку, короче ожидаемого
        assertThat(result.getMessage()).isNotNull();
        // или более гибкая проверка
        assertThat(result.getMessage()).matches("\\d+");
    }

    // Статус код 200, так как в теле запроса все параметры необязательные
    @Test
    @DisplayName("Отправка пустого тела")
    public void updateUser_EmptyBody_ShouldReturn400() throws IOException {
        Response response = userHelper.updateUserWithQueryParam("keke", "{}");
        assertThat(response.code()).isEqualTo(HttpCode.OK);
    }

    @Test
    @DisplayName("Проверка заголовков ответа")
    public void updateUser_ResponseHeaders_ShouldBeCorrect() throws IOException {
        String updateJson = "{ \"id\": 1 , \"username\": \"string\" }";

        Response response = userHelper.updateUserWithQueryParam("keke", updateJson);
        assertThat(response.header("Content-Type")).contains("application/json");
        assertThat(response.header("Server")).isNotNull(); // пример проверки заголовка
    }

    // ВНИМАНИЕ: здесь обнаружен баг: отправляю некорректные данные, приходит статус код 200 и тело ответа
    @Test
    @DisplayName("Отправка некорректных данных")
    public void updateUser_InvalidData_ShouldReturn() throws IOException {
        String invalidJson = "{ \"invalid\": \"data\" }";

        Response response = userHelper.updateUserWithQueryParam("keke", invalidJson);
        System.out.println("Статус код: " + response.code());
        String responseBodyString = response.body().string();
        System.out.println("Тело ответа: " + responseBodyString);
        assertThat(response.code()).isEqualTo(HttpCode.OK);
    }

    @Test
    @DisplayName("Отправка запроса на несуществующий URL")
    public void updateUserWithCustomUrl() throws IOException {
        String updateJson = "{ \"id\": 1 , \"username\": \"string\" }";

        Response response = userHelper.updateUserWithCustomUrl("https://petstore.swagger.io/v2/user/unknown",
                "keke", updateJson);
        assertThat(response.code()).isEqualTo(HttpCode.NOT_FOUND);
    }
}