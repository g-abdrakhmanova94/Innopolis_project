package tests.contract;

import helpers.HttpCode;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.assertj.core.api.Assertions.assertThat;

public class GetUserNameContractTest {
    private static final String URL = "https://petstore.swagger.io/";
    private OkHttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient();
    }

    @Test
    @DisplayName("Проверить статус код")
    public void checkStatusCode() throws IOException {
        String username = "string";
        String url = URL + "v2/user/" + username;

        Request request = new Request.Builder().url(url).build();

        Response response = httpClient.newCall(request).execute();
        assertThat(response.code()).isEqualTo(HttpCode.OK);
        response.close();
    }

    @Test
    @DisplayName("Проверить тело ответа (полученные данные пользователя)")
    public void checkResponseBody() throws IOException {
        String username = "string";
        String url = URL + "v2/user/" + username;

        Request request = new Request.Builder().url(url).build();

        Response response = httpClient.newCall(request).execute();
        System.out.println(response.body().string());
        response.close();
    }

    @Test
    @DisplayName("Проверить заголовки ответа")
    public void checkResponseHeaders() throws IOException {
        String username = "string";
        String url = URL + "v2/user/" + username;

        Request request = new Request.Builder().url(url).build();

        Response response = httpClient.newCall(request).execute();

        // Проверка наличия и значения заголовков
        assertThat(response.header("Content-Type")).contains("application/json");
        // Вывод всех заголовков
        response.headers().names().forEach(name -> System.out.println(name + ": " + response.header(name)));
        response.close();
    }

    @Test
    @DisplayName("Отправка GET-запроса без параметров")
    public void sendGetWithoutParameters() throws IOException {
        String url = URL + "v2/user"; // Без указания username
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        // Проверка, что запрос завершился успешно (например, статус 405 ИЛИ 404)
        assertThat(response.code()).isIn(HttpCode.BAD_REQUEST, HttpCode.METHOD_NOT_ALLOWED);
        response.close();
    }

    @Test
    @DisplayName("Запрос на несуществующий URL")
    public void requestToInvalidUrl() throws IOException {
        String invalidUrl = "https://petstore.swagger.io/v2/user/nonexistent";
        Request request = new Request.Builder()
                .url(invalidUrl)
                .addHeader("accept", "application/json")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();

        String body = response.body().string();
        System.out.println("Статус код: " + response.code());
        System.out.println("Тело ответа:\n" + body);

        response.close();
    }
    
    @Test
    @DisplayName("Отправить несуществующие данные")
    public void sendNonexistentData() throws IOException {
        String username = "l";
        String url = URL + "v2/user/" + username;

        Request request = new Request.Builder().url(url).build();

        Response response = httpClient.newCall(request).execute();
        System.out.println(response.body().string());
        response.close();
    }
}