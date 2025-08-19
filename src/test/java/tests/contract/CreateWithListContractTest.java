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
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateWithListContractTest {
    private static final String URL = "https://petstore.swagger.io/v2/user/createWithList";

    private OkHttpClient httpClient;
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient();
        objectMapper = new ObjectMapper();
        user = new User(1, "string", "string", "string",
                "string", "string", "string", 1);
    }

    @Test
    @DisplayName("Проверить статус код")
    public void createWithList_ShouldReturn200() throws IOException {
        String jsonArray = objectMapper.writeValueAsString(Arrays.asList(user));

        RequestBody requestBody = RequestBody.create(
                jsonArray,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();

        assertThat(response.code()).isEqualTo(HttpCode.OK);
        response.close();
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void verifyResponseBody_ShouldReturnExpectedJson() throws Exception {
        String jsonArray = objectMapper.writeValueAsString(Arrays.asList(user));

        RequestBody requestBody = RequestBody.create(
                jsonArray,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();

        String responseBody = response.body().string();

        JsonNode json = objectMapper.readTree(responseBody);

        assertThat(json.has("code")).isTrue();
        assertThat(json.get("code").asInt()).isEqualTo(HttpCode.OK);
        assertThat(json.has("type")).isTrue();
        assertThat(json.get("type").asText()).isEqualTo("unknown");
        assertThat(json.has("message")).isTrue();
        assertThat(json.get("message").asText()).isEqualTo("ok");
    }

    @Test
    @DisplayName("Отправить пустое тело")
    public void createWithList_EmptyBody_ShouldReturnError() throws IOException {
        RequestBody requestBody = RequestBody.create(
                "[{}]", // Пустое тело
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();

        assertThat(response.code()).isEqualTo(HttpCode.OK);
    }

    @Test
    @DisplayName("Отправить данные с неправильным типом")
    public void createWithList_WrongDataType_ShouldReturnError() throws IOException {
        String invalidData = "Это не JSON"; // Некорректный формат

        RequestBody requestBody = RequestBody.create(
                invalidData,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();

        // Предполагаемый код ошибки, например 400
        assertThat(response.code()).isIn(HttpCode.BAD_REQUEST);
        response.close();
    }
}