package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.HttpCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ToDoListPatchTest {
    private final static String URL = "https://todo-app-sky.herokuapp.com/";
    private HttpClient httpClient;
    private String existingTaskId;

    @BeforeEach
    public void setUp() throws IOException {
        httpClient = HttpClientBuilder.create().build();
        existingTaskId = getExistingTaskId();
    }

    private String getExistingTaskId() throws IOException {
        HttpGet request = new HttpGet(URL);
        HttpResponse response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        TodoItem[] items = mapper.readValue(responseBody, TodoItem[].class);
        if (items.length > 0) {
            return String.valueOf(items[0].getId()); // берем первый элемент
        } else {
            throw new RuntimeException("Нет задач для теста");
        }
    }

    @DisplayName("Переименование задачи, проверка статус кода")
    @Test
    public void renameTaskStatusCode() throws IOException {
        HttpPatch request = new HttpPatch(URL + existingTaskId);
        String newTitle = """
                {
                    "title": "Обновленное название задачи"
                }""";
        StringEntity entity = new StringEntity(newTitle, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }

    @DisplayName("Переименование задачи, проверка тела ответа")
    @Test
    public void renameTaskResponseBody() throws IOException {
        String updatedTitle = "Полностью новое название";

        HttpPatch request = new HttpPatch(URL + existingTaskId);
        String content = String.format("""
                {
                    "title": "%s"
                }""", updatedTitle);
        StringEntity entity = new StringEntity(content, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = EntityUtils.toString(response.getEntity());
        TodoItem updatedItem = mapper.readValue(responseBody, TodoItem.class);

        assertThat(updatedItem.getTitle()).isEqualTo(updatedTitle);
        assertThat(updatedItem.getId()).isEqualTo(Integer.parseInt(existingTaskId));
    }
}
