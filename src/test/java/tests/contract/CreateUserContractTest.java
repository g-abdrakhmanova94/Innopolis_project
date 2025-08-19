package tests.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.User;
import helpers.HttpCode;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserContractTest {
    private static final String URL = "https://petstore.swagger.io/v2/user";
    private OkHttpClient httpClient;
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient();
        objectMapper = new ObjectMapper();
        user = new User(1, "string", "string", "string", "string",
                "string", "string", 1);
    }

    @Test
    @DisplayName("Проверить статус код")
    public void createUser_ShouldReturnStatusCode200() throws IOException {
        String jsonBody = objectMapper.writeValueAsString(user);
        // Создать RequestBody
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json;" +
                " charset=utf-8"));

        // Построить запрос
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        // Выполнить запрос
        Response response = httpClient.newCall(request).execute();

        // Проверить статус код
        assertThat(response.code()).isEqualTo(HttpCode.OK);
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void verifyResponseBody() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(user);

        // Создать RequestBody
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json; " +
                "charset=utf-8"));

        // Построить запрос
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        // Выполнить запрос
        Response response = httpClient.newCall(request).execute();

        // Прочитать тело ответа
        String responseBody = response.body().string();

        // Использовать existing объект mapper
        JsonNode json = objectMapper.readTree(responseBody);
        assertThat(json.get("code").asInt()).isEqualTo(HttpCode.OK);
        assertThat(json.get("type").asText()).isEqualTo("unknown");
        assertThat(json.get("message").asInt()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("Создать запрос без тела")
    public void createUserWithoutBody_ShouldReturnStatusCode200() throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create("{}", MediaType.parse("application/json; charset=utf-8")))
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();

        assertThat(response.code()).isEqualTo(HttpCode.OK);
    }

    @Test
    @DisplayName("Проверить отправленные данные")
    public void verifySentData() throws IOException {
        // Проверить типы данных в объекте перед сериализацией
        assertThat(user.getId()).isInstanceOf(Integer.class);
        assertThat(user.getUsername()).isInstanceOf(String.class);
        assertThat(user.getUserStatus()).isInstanceOf(Integer.class);

        // Сериализация в JSON
        String jsonBody = objectMapper.writeValueAsString(user);

        // Проверить, что JSON содержит поля с нужными типами
        JsonNode jsonNode = objectMapper.readTree(jsonBody);
        assertThat(jsonNode.get("id").isInt()).isTrue();
        assertThat(jsonNode.get("username").isTextual()).isTrue();
        assertThat(jsonNode.get("userStatus").isInt()).isTrue();
    }
}