package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.HttpCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ToDoListPostTest {
    private final static String URL = "https://todo-app-sky.herokuapp.com/";

    private HttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = HttpClientBuilder.create().build();
    }

    @DisplayName("Создание новой задачи, проверка статус кода")
    @Test
    public void createTaskCode200() throws IOException {
        HttpPost request = new HttpPost(URL);
        String myContent = """
                {
                    "title": "Новая задача",
                    "completed": false
                }""";
        StringEntity stringEntity = new StringEntity(myContent, ContentType.APPLICATION_JSON);
        request.setEntity(stringEntity);

        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }

    @DisplayName("Создание новой задачи, проверка тела")
    @Test
    public void createTaskResponseBody() throws IOException {
        HttpPost request = new HttpPost(URL);
        String myContent = """
                 {
                    "title": "Новая задача",
                    "completed": false
                }""";
        StringEntity stringEntity = new StringEntity(myContent, ContentType.APPLICATION_JSON);
        request.setEntity(stringEntity);

        HttpResponse response = httpClient.execute(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String stringBody = EntityUtils.toString(response.getEntity());
        TodoItem todoItem = objectMapper.readValue(stringBody, TodoItem.class);
        assertThat(todoItem.getTitle()).isEqualTo("Новая задача");
    }
}
