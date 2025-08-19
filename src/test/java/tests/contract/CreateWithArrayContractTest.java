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

public class CreateWithArrayContractTest {
    private static final String URL = "https://petstore.swagger.io/v2/user/createWithArray";

    private OkHttpClient httpClient;
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient();
        objectMapper = new ObjectMapper();
        user = new User(3, "string", "string", "string", "string", "string", "string", 3);
    }

    @Test
    @DisplayName("Проверить статус код")
    public void createWithArray_ShouldReturn200() throws IOException {
        String jsonArray = objectMapper.writeValueAsString(Arrays.asList(user));

        Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create(jsonArray, MediaType.parse("application/json; charset=utf-8")))
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

        Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create(jsonArray, MediaType.parse("application/json; charset=utf-8")))
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseBody = response.body().string();
        response.close();

        JsonNode json = objectMapper.readTree(responseBody);
        assertThat(json.get("code").asInt()).isEqualTo(HttpCode.OK);
        assertThat(json.get("type").asText()).isEqualTo("unknown");
        assertThat(json.get("message").asText()).isEqualTo("ok");
    }

    @Test
    @DisplayName("Отправить пустой массив")
    public void createWithArray_EmptyArray_ShouldReturn200() throws IOException {
        String emptyJson = "[]";

        Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create(emptyJson, MediaType.parse("application/json; charset=utf-8")))
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();
        assertThat(response.code()).isEqualTo(HttpCode.OK);
        response.close();
    }

    @Test
    @DisplayName("Отправить некорректные данные")
    public void createWithArray_WrongData_ShouldReturnError() throws IOException {
        String invalidData = "Это не JSON";

        Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create(invalidData, MediaType.parse("application/json; charset=utf-8")))
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();
        assertThat(response.code()).isIn(HttpCode.BAD_REQUEST);
        response.close();
    }
}