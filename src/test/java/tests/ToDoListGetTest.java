package tests;

import helpers.HttpCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoListGetTest {
    private final static String URL = "https://todo-app-sky.herokuapp.com/";

    private HttpClient httpClient;
    private HttpGet request;

    @BeforeEach
    public void setUp() {
        httpClient = HttpClientBuilder.create().build();
        request = new HttpGet(URL);
    }

    @DisplayName("Получение статус кода списка задач")
    @Test
    public void sendGetTestCheckStatusCode() throws IOException {
        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }

    @DisplayName("Получение тела списка задач")
    @Test
    public void sendGetTestCheckBody() throws IOException {
        HttpResponse response = httpClient.execute(request);

        // Получение тела ответа в виде строки
        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

        // Проверка заголовка Content-Type
        assertEquals("application/json; charset=utf-8",
                response.getFirstHeader("Content-Type").getValue());

        // Проверка, что тело начинается и заканчивается квадратными скобками
        assertTrue(responseBody.startsWith("[") && responseBody.endsWith("]"),
                "Тело должно начинаться и заканчиваться квадратными скобками");
    }
}