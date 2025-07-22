package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.HttpCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ToDoListDeleteTest {
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

    @Test
    @DisplayName("Удаление задачи, проверка статус кода")
    public void deleteTaskStatusCode() throws IOException {
        HttpDelete request = new HttpDelete(URL + existingTaskId);
        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }

    @Test
    @DisplayName("Удаление задачи, проверка что задача исчезла")
    public void deleteTaskExistence() throws IOException {
        // Удаляем задачу
        HttpDelete deleteRequest = new HttpDelete(URL + existingTaskId);
        httpClient.execute(deleteRequest);

        // Проверяем, что задача больше не существует
        HttpGet getRequest = new HttpGet(URL);
        HttpResponse getResponse = httpClient.execute(getRequest);
        String responseBody = EntityUtils.toString(getResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        TodoItem[] items = mapper.readValue(responseBody, TodoItem[].class);

        boolean taskExists = false;
        for (TodoItem item : items) {
            if (item.getId() == Integer.parseInt(existingTaskId)) {
                taskExists = true;
                break;
            }
        }
        assertThat(taskExists).isFalse();
    }
}

