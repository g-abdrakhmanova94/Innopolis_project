package tests.contract;

import helpers.HttpCode;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetLoginContractTest {
    private static final String URL = "https://petstore.swagger.io";
    private OkHttpClient httpClient;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient();
    }

    @Test
    @DisplayName("Проверить статус код")
    public void checkStatusCode() throws IOException {
        HttpUrl url = HttpUrl.parse(URL + "/v2/user/login").newBuilder()
                .addQueryParameter("username", "KUKU")
                .addQueryParameter("password", "123")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        assertThat(response.code()).isEqualTo(HttpCode.OK);
        response.close();
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void checkResponseBody() throws IOException {
        HttpUrl url = HttpUrl.parse(URL + "/v2/user/login").newBuilder()
                .addQueryParameter("username", "KUKU")
                .addQueryParameter("password", "123")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();

        String body = response.body().string();
        System.out.println("Тело ответа в JSON:\n" + body);
        response.close();
    }


    @Test
    @DisplayName("Проверить заголовки ответа")
    public void checkResponseHeaders() throws IOException {
        HttpUrl url = HttpUrl.parse(URL + "/v2/user/login").newBuilder()
                .addQueryParameter("username", "KUKU")
                .addQueryParameter("password", "123")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();

        // Вывод всех заголовков
        response.headers().names().forEach(name ->
                System.out.println(name + ": " + response.header(name))
        );

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

    // ВНИМАНИЕ: здесь обнаружен баг: не отправляю обязательные параметры запроса, приходит статус код 200 и тело ответа
    @Test
    @DisplayName("Отправить пустой запрос")
    public void sendEmptyData() throws IOException {
        HttpUrl url = HttpUrl.parse(URL + "/v2/user/login");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();

        String body = response.body().string();
        System.out.println("Статус код: " + response.code());
        System.out.println("Тело ответа:\n" + body);

        response.close();
    }

    // ВНИМАНИЕ: здесь обнаружен баг: отправляю несуществующие параметры запроса, приходит статус код 200 и тело ответа
    @Test
    @DisplayName("Отправить несуществующие данные")
    public void sendNonexistentData() throws IOException {
        HttpUrl url = HttpUrl.parse(URL + "/v2/user/login").newBuilder()
                .addQueryParameter("username", "NONEXISTENTUSER")
                .addQueryParameter("password", "WRONGPASS")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();

        String body = response.body().string();
        System.out.println("Статус код: " + response.code());
        System.out.println("Тело ответа:\n" + body);

        response.close();
    }
}